import { makeRequest } from "$lib/spotify/api";
import { artist } from "$lib/spotify/converter";
import { writable } from "svelte/store";
import { StoreState } from "$lib/stores/types";

type ArtistStore = {  

  artists: Artist[];
  total_artists?: number;
  lastUpdated: Date | null;
  status: StoreState
}

export const artistStore = writable<ArtistStore>({
  artists: [],
  total_artists: 0,
  lastUpdated: null,
  status: StoreState.Uninitialized
})

function upadteStatus(status: StoreState) {
  artistStore.update((s) => ({...s, status: status}))
}

function addArtists(as: Artist[]) {
  artistStore.update((s) => ({...s, artists: [...s.artists, ...as]}));
}

function setTotal(total?: number) {
  artistStore.update((p) => ({...p, total_artists: total}));
}

function refreshLastUpdated() {
  artistStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}

export module Artists {
  
  export async function loadAll() {
    try {
      upadteStatus(StoreState.Loading);

      await getAll();

      refreshLastUpdated();
      upadteStatus(StoreState.Ready);
    } catch (error) {
      upadteStatus(StoreState.Error);
    }
  }

  async function getAll() {
    let after: string | null = null;
    let next: string;
    let artists: Artist[] = []

    do {
      const data = await makeRequest((api) => api.getFollowedArtists({ limit: 50, ...(after && { after: after }) }));
      after = data.artists.cursors.after
		  next = data.artists.next;      
      addArtists(data.artists.items.map((d) => artist(d)));
      setTotal(data.artists.total);
    } while(next)

    return artists;
  }

}
