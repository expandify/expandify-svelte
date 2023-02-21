import { makeRequest } from "../request";
import { loadSeveralTracks } from "./tracks";

export async function loadAlbumWithTracks(id: string) {
  const album = await makeRequest((api) => api.getAlbum(id));
  const tracks = await loadAlbumTracks(album);
  
  return { album: album, tracks: tracks };
}

type LoadingCallback = (album: SpotifyApi.SavedAlbumObject, tracks: SpotifyApi.TrackObjectFull[], total: number) => Promise<void>;
export async function loadSavedAlbumsWithTracks(callback: LoadingCallback) {
  let offset = 0;
  let next: string;

  do {
    const data = await makeRequest((api) => api.getMySavedAlbums({ limit: 50, offset: offset }));
    offset += data.limit;
    next = data.next;
    
    for (const album of data.items) {       
      const tracks = await loadAlbumTracks(album.album);
      await callback(album, tracks, data.total); 
    }
  } while(next)
}

async function loadAlbumTracks(albumFull: SpotifyApi.AlbumObjectFull) {
  let next = albumFull.tracks.next;
  let offset = albumFull.tracks.items.length;
  let tracks: SpotifyApi.TrackObjectFull[] = [];

  await loadSeveralTracks(albumFull.tracks.items.map(t => t.id), async (ts, _) => { tracks = [...tracks, ts]});
    
  while(next) {
    const data = await makeRequest((api) => api.getAlbumTracks(albumFull.id, { limit: 50, offset }));
    offset += data.limit;
    next = data.next;
    await loadSeveralTracks(data.items.map(t => t.id), async (ts, _) => { tracks = [...tracks, ts]});
  }    

  return tracks;
}
