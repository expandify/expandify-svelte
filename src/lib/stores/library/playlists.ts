
import { makeRequest } from "$lib/spotify/api";
import { playlist, playlistSimplified, playlistTrack, userPrivate } from "$lib/spotify/converter";
import { get, writable } from "svelte/store";
import { Indicator } from "../indicators";
import { User, userStore } from "./user";

const enum State {
  Uninitialized = 'Uninitialized',
  Ready = 'Ready',
  Error = 'Error',  
}

type PlaylistStore = {  
  playlists: Playlist[];
  lastUpdated: Date | null;
  status: State
}

export const playlistStore = writable<PlaylistStore>({
  playlists: [],  
  lastUpdated: null,
  status: State.Uninitialized
})


export module Playlists {
  
  export async function loadAll() {
    try {
      playlistStore.update((s) => ({...s, status: State.Uninitialized}))
      const ps = await getAllWithTracks();
      await fillStore(ps);
      Indicator.addSuccess("Playlists ready!")
    } catch (error) {
      playlistStore.update((s) => ({...s, status: State.Error}))
      Indicator.addError("Error loading Playlists");
    }
  }

  async function getAllWithTracks() {
    const playlists: Playlist[] = []
    const simplePlaylists = await getUserPlaylists();
    
    let {update, stop} = Indicator.addLoading("Loading Playlist Tracks");
    let i = 1;
    for (const simplePlaylist of simplePlaylists) {
      const playlist = await getPlaylistWithTracks(simplePlaylist);
      playlists.push(playlist);
      update(++i, simplePlaylists.length);
    } 
    stop();
    Indicator.addAnnouncement("All Playlist Tracks Loaded");
    return playlists;
  }

  async function getUserPlaylists() {
    let offset = 0;
    let next: string;
    let playlists: PlaylistSimplified[] = []

    let {update, stop} = Indicator.addLoading("Loading Playlists");
    do {
      // Typescript throws a warning: {} not assignable to string
      // This can be ignored, since the library does some fuckery
      // @ts-ignore
      const data = await makeRequest((api) => api.getUserPlaylists({ limit: 50, offset: offset }));
      offset += data.limit;
		  next = data.next;
      playlists = [...playlists, ...data.items.map(d => playlistSimplified(d))];
      update(playlists.length, data.total);
    } while(next)

    stop();
    Indicator.addAnnouncement("Playlists Loaded");
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

  async function fillStore(ps: Playlist[]) { 
    playlistStore.set({
      playlists: ps,
      lastUpdated: new Date(Date.now()),
      status: State.Ready
    })
  }

}
