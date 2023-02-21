import type { Artist } from "$lib/types/spotify";
import type { ArtistStore, StoreError } from "$lib/types/library-stores";
import { writable } from "svelte/store";



function createStore() {  
  const data: ArtistStore = {artists: [], total: 0, loading: false, updated: null, error: null };
  const { subscribe, set, update } = writable(data);
	return {
		subscribe,
    addArtists: (artists: Artist[]) => update(s => ({...s, artists: [...s.artists, ...artists]})),
    addArtist: (artist: Artist) => update(s => ({...s, artists: [...s.artists, artist]})),
		setTotal: (total: number) => update(s => ({...s, total: total})),
		startLoading: () => update(_ => ({...data, loading: true})),
    stopLoading: () => update(s => ({...s, loading: false, updated: new Date(Date.now())})),
    setError: (e: StoreError | null) => update(s => ({...s, error: e})),
		reset: () => set(data)
	};
}

export const artists = createStore();

