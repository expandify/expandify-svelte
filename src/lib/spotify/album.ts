import { Album } from "$lib/classes/Album";
import { Track } from "$lib/classes/Track";
import { albumCache } from "$lib/stores/cache"
import type SpotifyWebApi from "spotify-web-api-js"
import { makePagingRequest } from "./request"

export async function reloadSavedAlbumsWithTracks() {
  const func = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => api.getMySavedAlbums({limit: 50, offset})
  const albums = await makePagingRequest(func);


  for (const album of albums) {    

    const tracksFunc = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => api.getAlbumTracks(album.album.id, {limit: 50, offset})  
    const albumTracks = await makePagingRequest(tracksFunc, (_) => {}, album.album.tracks);
    const tracks = albumTracks.map(t => Track.fromSimplifiedTrack(t));

    albumCache.update(current => [...current, Album.fromSavedAlbum(album, tracks)]);
  }  
}



