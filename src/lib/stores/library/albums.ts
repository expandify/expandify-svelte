
import { makeRequest } from "$lib/spotify/api";
import { savedAlbum, trackSimplified } from "$lib/spotify/converter";
import { writable } from "svelte/store";
import { Indicator } from "../indicators";

const enum State {
  Uninitialized = 'Uninitialized',
  Ready = 'Ready',
  Error = 'Error',  
}

type AlbumStore = {  

  albums: SavedAlbum[];
  
  lastUpdated: Date | null;
  status: State
}

export const albumStore = writable<AlbumStore>({
  albums: [],
  lastUpdated: null,
  status: State.Uninitialized
})


export module Albums {
  
  export async function loadAll() {
    try {
      albumStore.update((s) => ({...s, status: State.Uninitialized}))
      const ps = await getAllWithTracks();
      await fillStore(ps);
      Indicator.addSuccess("Albums ready!")
    } catch (error) {
      albumStore.update((s) => ({...s, status: State.Error}))
      Indicator.addError("Error Loading Albums");
    }
  }

  async function getAllWithTracks() {
    const albums: SavedAlbum[] = []
    const savedAlbums = await getSavedAlbums();
    
    let {update, stop} = Indicator.addLoading("Loading Album Tracks");
    let i = 1;
    for (const album of savedAlbums) {
      const tracks = await getAlbumTracks(album);
      albums.push(savedAlbum(album, tracks));
      update(++i, savedAlbums.length);
    } 
    stop();
    Indicator.addAnnouncement("All Album Tracks Loaded");
    return albums;
  }

  async function getSavedAlbums() {
    let offset = 0;
    let next: string;
    let albums: SpotifyApi.SavedAlbumObject [] = []

    let {update, stop} = Indicator.addLoading("Loading Albums");
    do {
      const data = await makeRequest((api) => api.getMySavedAlbums({ limit: 50, offset: offset }));
      offset += data.limit;
		  next = data.next;
      albums = [...albums, ...data.items];
      update(albums.length, data.total);
    } while(next)

    stop();
    Indicator.addAnnouncement("Albums Loaded");
    return albums;
  }

  async function getAlbumTracks(savedAlbum: SpotifyApi.SavedAlbumObject) {
    let next = savedAlbum.album.tracks.next;
    let offset = 0;
    let tracks: TrackSimplified[] = savedAlbum.album.tracks.items.map(t => trackSimplified(t));

    while(next) {
      const data = await makeRequest((api) => api.getAlbumTracks(savedAlbum.album.id, { limit: 50, offset }));
      offset += data.limit;
		  next = data.next;
      tracks = [...tracks, ...data.items.map(d => trackSimplified(d))]
    }

    return tracks;
  }

  async function fillStore(ps: SavedAlbum[]) {
    albumStore.set({
      albums: ps,      
      lastUpdated: new Date(Date.now()),
      status: State.Ready
    })
  }

}
