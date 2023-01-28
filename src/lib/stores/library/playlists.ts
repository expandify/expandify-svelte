import { StoreState } from "$lib/stores/types";
import { makeRequest } from "$lib/spotify/api";
import { playlist, playlistSimplified, playlistTrack } from "$lib/spotify/converter";
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

function upadteStatus(status: StoreState) {
  playlistStore.update((s) => ({...s, status: status}))
}

function addPlaylist(playlist: Playlist) {
  playlistStore.update((s) => ({...s, playlists: [...s.playlists, playlist]}));
}

function setTotal(total: number) {
  playlistStore.update((p) => ({...p, total_playlists: total}));
}

function refreshLastUpdated() {
  playlistStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}

export module Playlists {

  export async function loadAll() {
    try {
      upadteStatus(StoreState.Loading);

      await loadAllWithTracks();

      refreshLastUpdated();
      upadteStatus(StoreState.Ready);
    } catch (error) {
      upadteStatus(StoreState.Error);
    }
  }

  async function loadAllWithTracks() {
    const simplePlaylists = await getUserPlaylists();
    setTotal(simplePlaylists.length);

    for (const simplePlaylist of simplePlaylists) {
      const playlist = await getPlaylistWithTracks(simplePlaylist);
      addPlaylist(playlist);
    } 
  }

  async function getUserPlaylists() {
    let offset = 0;
    let next: string;
    let playlists: PlaylistSimplified[] = []
    
    do {
      // Typescript throws a warning: {} not assignable to string
      // This can be ignored, since the library does some fuckery
      // @ts-ignore
      const data = await makeRequest((api) => api.getUserPlaylists({ limit: 50, offset: offset }));
      offset += data.limit;
		  next = data.next;
      playlists = [...playlists, ...data.items.map(d => playlistSimplified(d))];
    } while(next)
    return playlists;
  }

  async function getPlaylistWithTracks(playlistSimplified: PlaylistSimplified) {
    const playlistFull = await makeRequest((api) => api.getPlaylist(playlistSimplified.id));

    let next = playlistFull.tracks.next;
    let offset = 0;
    let tracks: PlaylistTrack[] = [];

    while(next) {
      const data = await makeRequest((api) => api.getPlaylistTracks(playlistSimplified.id, { limit: 50, offset }));
      offset += data.limit;
		  next = data.next;
      tracks = [...tracks, ...data.items.map(d => playlistTrack(d))]
    }

    return playlist(playlistFull, tracks);
  }

}
