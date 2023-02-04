import { updateStatus, setUpdatedNow, setTotal, addArtists, clearArtists } from "$lib/stores/library/artists"
import { StoreState } from "$lib/stores/types";
import { error } from "@sveltejs/kit";
import { artist } from "../converter";
import { makeRequest } from "../request";

export async function loadFollowedArtists() {
  clearArtists();
  updateStatus(StoreState.Loading);

  try {
    await getAll();    
  } catch (err) {
    updateStatus(StoreState.Error);
    throw error(500, {message: "Error loading Artists", hiddenError: err})
  }

  setUpdatedNow();
  updateStatus(StoreState.Ready);
}

async function getAll() {
  let after: string | null = null;
  let next: string;
  let artists: Artist[] = []

  do {
    const data = await makeRequest((api) => api.getFollowedArtists({ limit: 50, ...(after && { after: after }) }));
    after = data.artists.cursors.after
    next = data.artists.next;      
    addArtists(data.artists.items.map((d) => artist(d)));
    setTotal(data.artists.total);
  } while(next)

  return artists;
}