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

export function clearTracks() {
  trackStore.set({
    tracks: [],
    total_tracks: 0,
    lastUpdated: null,
    status: StoreState.Uninitialized
  });
}

export function updateStatus(status: StoreState) {
  trackStore.update((s) => ({...s, status: status}))
}

export function addTracks(ts: SavedTrack[]) {
  trackStore.update((s) => ({...s, tracks: [...s.tracks, ...ts]}));
}

export function setTotal(total: number) {
  trackStore.update((p) => ({...p, total_tracks: total}));
}

export function setUpdatedNow() {
  trackStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}

