import { makeRequest } from "$lib/spotify/api";
import { savedAlbum, trackSimplified } from "$lib/spotify/converter";
import { StoreState } from "$lib/stores/types";
import { writable } from "svelte/store";



type AlbumStore = {  

  albums: SavedAlbum[];
  total_albums: number;
  lastUpdated: Date | null;
  status: StoreState
}

export const albumStore = writable<AlbumStore>({
  albums: [],
  total_albums: 0,
  lastUpdated: null,
  status: StoreState.Uninitialized
})

function upadteStatus(status: StoreState) {
  albumStore.update((s) => ({...s, status: status}))
}

function addAlbum(album: SavedAlbum) {
  albumStore.update((s) => ({...s, albums: [...s.albums, album]}));
}

function setTotal(total: number) {
  albumStore.update((p) => ({...p, total_albums: total}));
}

function refreshLastUpdated() {
  albumStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}


export module Albums {
  
  export async function loadAll() {
    try {
      upadteStatus(StoreState.Loading);

      await getAllWithTracks();

      refreshLastUpdated();
      upadteStatus(StoreState.Ready);
    } catch (error) {
      upadteStatus(StoreState.Ready);
    }
  }

  async function getAllWithTracks() {
    const savedAlbums = await getSavedAlbums();    
    setTotal(savedAlbum.length);

    for (const album of savedAlbums) {
      const tracks = await getAlbumTracks(album);
      addAlbum(savedAlbum(album, tracks));
    } 
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

}
