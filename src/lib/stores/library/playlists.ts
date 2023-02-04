import { StoreState } from "$lib/stores/types";
import { writable } from "svelte/store";



type PlaylistStore = {  
  playlists: Playlist[];
  total_playlists: number;
  lastUpdated: Date | null;
  status: StoreState
}

export const playlistStore = writable<PlaylistStore>({
  playlists: [],  
  total_playlists: 0,
  lastUpdated: null,
  status: StoreState.Uninitialized
})

export function clearPlaylists() {
  playlistStore.set({
    playlists: [],  
    total_playlists: 0,
    lastUpdated: null,
    status: StoreState.Uninitialized
  });
}

export function updateStatus(status: StoreState) {
  playlistStore.update((s) => ({...s, status: status}))
}

export function addPlaylist(playlist: Playlist) {
  playlistStore.update((s) => ({...s, playlists: [...s.playlists, playlist]}));
}

export function setTotal(total: number) {
  playlistStore.update((p) => ({...p, total_playlists: total}));
}

export function setUpdatedNow() {
  playlistStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}

