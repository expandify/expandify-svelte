import { updateStatus, setUpdatedNow, setTotal, addTracks, clearTracks } from "$lib/stores/library/tracks"
import { StoreState } from "$lib/stores/types";
import { error } from "@sveltejs/kit";
import { savedTrack } from "../converter";
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
