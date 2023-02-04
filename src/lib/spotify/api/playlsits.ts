import { updateStatus, setUpdatedNow, setTotal, clearPlaylists, addPlaylist } from "$lib/stores/library/playlists"
import { StoreState } from "$lib/stores/types";
import { error } from "@sveltejs/kit";
import { playlist, playlistSimplified, playlistTrack } from "../converter";
import { makeRequest } from "../request";

export async function loadUserPlaylistsWithTracks() {
  clearPlaylists();
  updateStatus(StoreState.Loading);

  try {

    const simplePlaylists = await getUserPlaylists();
    setTotal(simplePlaylists.length);

    for (const simplePlaylist of simplePlaylists) {
      const playlist = await getPlaylistWithTracks(simplePlaylist);
      addPlaylist(playlist);
    } 
    
  } catch (err) {
    updateStatus(StoreState.Error);
    throw error(500, {message: "Error loading Playlists", hiddenError: err});
  }

  setUpdatedNow();
  updateStatus(StoreState.Ready);
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


