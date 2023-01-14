import { handleRequest, makePagingRequest } from "./request"
import { playlistCache } from "$lib/stores/cache"
import type SpotifyWebApi from "spotify-web-api-js"
import { Track } from "$lib/classes/Track"
import { PlaylistTrack } from "$lib/classes/PlaylistTrack"
import { Playlist } from "$lib/classes/Playlist"


export async function reloadUserPlaylists() {
  // Typescript throws a warning: {} not assignable to string
  // This can be ignored, since the library does some fuckery
  // @ts-ignore  
  const playlistFunc = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => { return api.getUserPlaylists({limit: 50, offset}) }
  const playlistsSimple = await makePagingRequest(playlistFunc);


  for (const playlist of playlistsSimple) {    
    const playlistFull = await handleRequest((api) => api.getPlaylist(playlist.id)); 

    const playlistTrackFunc = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => api.getPlaylistTracks(playlist.id, {limit: 50, offset})  
    const playlistTracks = await makePagingRequest(playlistTrackFunc);
    const tracks = playlistTracks.map(t => PlaylistTrack.fromPlaylistTrackObject(t));

    playlistCache.update(current => [...current, Playlist.fromFullPlaylist(playlistFull, tracks)]);
  }   
}