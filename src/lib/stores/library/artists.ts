
import { makeRequest } from "$lib/spotify/api";
import { artist } from "$lib/spotify/converter";
import { writable } from "svelte/store";
import { Indicator } from "../indicators";

const enum State {
  Uninitialized = 'Uninitialized',
  Ready = 'Ready',
  Error = 'Error',  
}

type ArtistStore = {  

  artists: Artist[];
  
  lastUpdated: Date | null;
  status: State
}

export const artistStore = writable<ArtistStore>({
  artists: [],
  lastUpdated: null,
  status: State.Uninitialized
})


export module Artists {
  
  export async function loadAll() {
    try {
      artistStore.update((s) => ({...s, status: State.Uninitialized}))
      const as = await getAll();
      await fillStore(as);
      Indicator.addSuccess("Artists ready!")
    } catch (error) {
      artistStore.update((s) => ({...s, status: State.Error}))
      Indicator.addError("Error Loading Artists");
    }
  }

  async function getAll() {
    let after: string | null = null;
    let next: string;
    let artists: Artist[] = []

    let {update, stop} = Indicator.addLoading("Loading Artists");
    do {
      const data = await makeRequest((api) => api.getFollowedArtists({ limit: 50, ...(after && { after: after }) }));
      after = data.artists.cursors.after
		  next = data.artists.next;
      artists = [...artists, ...data.artists.items.map((d) => artist(d))];
      update(artists.length, data.artists.total || 0);
    } while(next)

    stop();
    Indicator.addAnnouncement("Artists Loaded");
    return artists;
  }

  async function fillStore(as: Artist[]) {
    artistStore.set({
      artists: as,      
      lastUpdated: new Date(Date.now()),
      status: State.Ready
    })
  }

}
