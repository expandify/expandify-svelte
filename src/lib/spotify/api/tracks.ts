import { updateStatus, setUpdatedNow, setTotal, addTracks, clearTracks } from "$lib/stores/library/tracks"
import { StoreState } from "$lib/stores/types";
import { error } from "@sveltejs/kit";
import { savedTrack, track } from "../converter";
import { makeRequest } from "../request";


export async function loadSavedTracks() {
  clearTracks();
  updateStatus(StoreState.Loading);

  try {
    await getAll();        
  } catch (err) {
    updateStatus(StoreState.Error);
    throw error(500, {message: "Error loading Tracks", hiddenError: err});    
  }

  setUpdatedNow();
    updateStatus(StoreState.Ready);
}


export async function getSeveralTracks(tracks: TrackSimplified[]) {
  const chunkSize = 50;
  const trackIds = tracks.map(t => t.id);

  let result: Track[] = []
  for (let i = 0; i < trackIds.length; i += chunkSize) {
      const chunk = trackIds.slice(i, i + chunkSize);
      
      const data = await makeRequest((api) => api.getTracks(chunk));
      result = [...result, ...data.tracks.map((t) => track(t))];
  }

  return result;
}

async function getAll() {
  let offset = 0;
  let next: string;

  do {
    const data = await makeRequest((api) => api.getMySavedTracks({ limit: 50, offset }));
    offset += data.limit;
    next = data.next;
    addTracks(data.items.map((d) => savedTrack(d)));
    setTotal(data.total);
  } while(next)
}
