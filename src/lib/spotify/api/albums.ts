import { updateStatus, setUpdatedNow, setTotal, addAlbum, clearAlbums } from "$lib/stores/library/albums"
import { StoreState } from "$lib/stores/types";
import { error } from "@sveltejs/kit";
import { album, savedAlbum, trackSimplified } from "../converter";
import { makeRequest } from "../request";
import { getSeveralTracks } from "./tracks";

export async function loadSavedAlbumsWithTracks() {  
  clearAlbums();
  updateStatus(StoreState.Loading);

  try {  
    const savedAlbums = await getSavedAlbums();    
    setTotal(savedAlbum.length);

    for (const album of savedAlbums) {
      const tracks = await getAlbumTracks(album.album);
      addAlbum(savedAlbum(album, tracks));
    }     
  } catch (err) {
    updateStatus(StoreState.Error);
    throw error(500, {message: "Error loading Albums", hiddenError: err});
  }

  setUpdatedNow();
  updateStatus(StoreState.Ready);  
}

export async function getAlbumWithTracks(id: string) {
  const a = await makeRequest((api) => api.getAlbum(id));
  const tracks = await getAlbumTracks(a);
  
  return album(a, tracks);
}

async function getSavedAlbums() {
  let offset = 0;
  let next: string;
  let albums: SpotifyApi.SavedAlbumObject [] = []

  do {
    const data = await makeRequest((api) => api.getMySavedAlbums({ limit: 50, offset: offset }));
    offset += data.limit;
    next = data.next;
    albums = [...albums, ...data.items];
  } while(next)

  return albums;
}

async function getAlbumTracks(albumFull: SpotifyApi.AlbumObjectFull) {
  let next = albumFull.tracks.next;
  let offset = albumFull.tracks.items.length;
  let tracks: TrackSimplified[] = albumFull.tracks.items.map(t => trackSimplified(t));
  
  while(next) {
    const data = await makeRequest((api) => api.getAlbumTracks(albumFull.id, { limit: 50, offset }));
    offset += data.limit;
    next = data.next;
    tracks = [...tracks, ...data.items.map(d => trackSimplified(d))]
    
  }

  return await getSeveralTracks(tracks);
}
