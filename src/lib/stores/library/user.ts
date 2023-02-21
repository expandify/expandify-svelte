import type { UserPrivate } from "$lib/types/spotify";
import type { StoreError, UserStore } from "$lib/types/library-stores";
import { writable } from "svelte/store";



function createStore() {  
  const data: UserStore = {user: null, loading: false, updated: null, error: null };
  const { subscribe, set, update } = writable(data);
	return {
		subscribe,
    setUser: (user: UserPrivate | null) => update(s => ({...s, user: user})),
		startLoading: () => update(_ => ({...data, loading: true})),
    stopLoading: () => update(s => ({...s, loading: false, updated: new Date(Date.now())})),
    setError: (e: StoreError | null) => update(s => ({...s, error: e})),
		reset: () => set(data)
	};
}

export const user = createStore();

