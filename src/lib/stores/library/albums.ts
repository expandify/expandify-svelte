import type { Album } from "$lib/types/spotify";
import type { AlbumStore, StoreError } from "$lib/types/library-stores";
import { writable } from "svelte/store";



function createStore() {  
  const data: AlbumStore = {albums: [], total: 0, loading: false, updated: null, error: null };
  const { subscribe, set, update } = writable(data);
	return {
		subscribe,
    addAlbums: (albums: Album[]) => update(s => ({...s, albums: [...s.albums, ...albums]})),
    addAlbum: (album: Album) => update(s => ({...s, albums: [...s.albums, album]})),
		setTotal: (total: number) => update(s => ({...s, total: total})),
		startLoading: () => update(_ => ({...data, loading: true})),
    stopLoading: () => update(s => ({...s, loading: false, updated: new Date(Date.now())})),
    setError: (e: StoreError | null) => update(s => ({...s, error: e})),
	};
}

export const albums = createStore();

