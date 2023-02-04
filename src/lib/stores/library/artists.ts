import { writable } from "svelte/store";
import { StoreState } from "$lib/stores/types";

type ArtistStore = {  

  artists: Artist[];
  total_artists?: number;
  lastUpdated: Date | null;
  status: StoreState
}

export const artistStore = writable<ArtistStore>({
  artists: [],
  total_artists: 0,
  lastUpdated: null,
  status: StoreState.Uninitialized
})

export function clearArtists() {
  artistStore.set({
    artists: [],
    total_artists: 0,
    lastUpdated: null,
    status: StoreState.Uninitialized
  });
}

export function updateStatus(status: StoreState) {
  artistStore.update((s) => ({...s, status: status}))
}

export function addArtists(as: Artist[]) {
  artistStore.update((s) => ({...s, artists: [...s.artists, ...as]}));
}

export function setTotal(total?: number) {
  artistStore.update((p) => ({...p, total_artists: total}));
}

export function setUpdatedNow() {
  artistStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}


