import { StoreState } from "$lib/stores/types";
import { writable } from "svelte/store";

type AlbumStore = {  

  albums: SavedAlbum[];
  total_albums: number;
  lastUpdated: Date | null;
  status: StoreState
}

export const albumStore = writable<AlbumStore>({
  albums: [],
  total_albums: 0,
  lastUpdated: null,
  status: StoreState.Uninitialized
})

export function clearAlbums() {
 albumStore.set({
    albums: [],
    total_albums: 0,
    lastUpdated: null,
    status: StoreState.Uninitialized
  });
}

export function updateStatus(status: StoreState) {
  albumStore.update((s) => ({...s, status: status}))
}

export function addAlbum(album: SavedAlbum) {
  albumStore.update((s) => ({...s, albums: [...s.albums, album]}));
}

export function setTotal(total: number) {
  albumStore.update((p) => ({...p, total_albums: total}));
}

export function setUpdatedNow() {
  albumStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}

