import { makeRequest } from "$lib/spotify/api";
import { savedTrack } from "$lib/spotify/converter";
import { writable } from "svelte/store";
import { StoreState } from "$lib/stores/types";


type TrackStore = {  

  tracks: SavedTrack[];
  total_tracks: number;
  lastUpdated: Date | null;
  status: StoreState
}

export const trackStore = writable<TrackStore>({
  tracks: [],
  total_tracks: 0,
  lastUpdated: null,
  status: StoreState.Uninitialized
})

function upadteStatus(status: StoreState) {
  trackStore.update((s) => ({...s, status: status}))
}

function addTracks(ts: SavedTrack[]) {
  trackStore.update((s) => ({...s, tracks: [...s.tracks, ...ts]}));
}

function setTotal(total: number) {
  trackStore.update((p) => ({...p, total_tracks: total}));
}

function refreshLastUpdated() {
  trackStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}

export module Tracks {
  
  export async function loadAll() {
    try {
      upadteStatus(StoreState.Loading);

      await getAll();
      
      refreshLastUpdated();
      upadteStatus(StoreState.Ready);
    } catch (error) {
      upadteStatus(StoreState.Error);
    }
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

}
