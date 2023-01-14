import { handleRequest, makePagingRequest } from "./request"
import { Cache } from "$lib/stores/cache"
import type SpotifyWebApi from "spotify-web-api-js"
import { PlaylistTrack } from "$lib/classes/PlaylistTrack"
import { Playlist } from "$lib/classes/Playlist"


export async function reloadUserPlaylistsWithTracks() {

  Cache.clear(Cache.playlists);
  Cache.setState(Cache.playlists, Cache.State.Loading);  
  let currentLoaded = 0;

  // Typescript throws a warning: {} not assignable to string
  // This can be ignored, since the library does some fuckery
  // @ts-ignore  
  const playlistFunc = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => { return api.getUserPlaylists({limit: 50, offset}) }
  const playlistsSimple = await makePagingRequest(playlistFunc, (page) => {
    currentLoaded = currentLoaded + page.items.length;
    Cache.setTotalItems(Cache.playlists, page.total);
    Cache.setLoadedItems(Cache.playlists, currentLoaded);
  });


  Cache.setState(Cache.playlists, Cache.State.LoadingSecondary);
  currentLoaded = 0;
  Cache.setTotalItems(Cache.playlists, playlistsSimple.length);
  Cache.setLoadedItems(Cache.playlists, currentLoaded);

  for (const playlist of playlistsSimple) {    
    const playlistFull = await handleRequest((api) => api.getPlaylist(playlist.id)); 

    const playlistTrackFunc = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => api.getPlaylistTracks(playlist.id, {limit: 50, offset})  
    const playlistTracks = await makePagingRequest(playlistTrackFunc, (_) => {}, playlistFull.tracks);
    const tracks = playlistTracks.map(t => PlaylistTrack.fromPlaylistTrackObject(t));

    const toCache = Playlist.fromFullPlaylist(playlistFull, tracks);

    currentLoaded++;
    Cache.setLoadedItems(Cache.playlists, currentLoaded);
    Cache.addItem(Cache.playlists, toCache);
  }   

  Cache.setState(Cache.playlists, Cache.State.Finished);  
}