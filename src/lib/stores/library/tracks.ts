import type { SavedTrack } from "$lib/types/spotify";
import type { StoreError, TrackStore } from "$lib/types/library-stores";
import { writable } from "svelte/store";



function createStore() {  
  const data: TrackStore = {tracks: [], total: 0, loading: false, updated: null, error: null };
  const { subscribe, set, update } = writable(data);
	return {
		subscribe,
    addTracks: (tracks: SavedTrack[]) => update(s => ({...s, tracks: [...s.tracks, ...tracks]})),
    addTrack: (track: SavedTrack) => update(s => ({...s, tracks: [...s.tracks, track]})),
		setTotal: (total: number) => update(s => ({...s, total: total})),
		startLoading: () => update(_ => ({...data, loading: true})),
    stopLoading: () => update(s => ({...s, loading: false, updated: new Date(Date.now())})),
    setError: (e: StoreError | null) => update(s => ({...s, error: e})),
		reset: () => set(data)
	};
}

export const tracks = createStore();

