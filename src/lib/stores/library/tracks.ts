
import { makeRequest } from "$lib/spotify/api";
import { savedTrack } from "$lib/spotify/converter";
import { writable } from "svelte/store";
import { Indicator } from "../indicators";

const enum State {
  Uninitialized = 'Uninitialized',
  Ready = 'Ready',
  Error = 'Error',  
}

type TrackStore = {  

  tracks: SavedTrack[];
  
  lastUpdated: Date | null;
  status: State
}

export const trackStore = writable<TrackStore>({
  tracks: [],
  lastUpdated: null,
  status: State.Uninitialized
})


export module Tracks {
  
  export async function loadAll() {
    try {
      trackStore.update((t) => ({...t, status: State.Uninitialized}))
      const ts = await getAll();
      await fillStore(ts);
      Indicator.addSuccess("Tracks ready!")
    } catch (error) {
      trackStore.update((s) => ({...s, status: State.Error}))
      Indicator.addError("Error Loading Tracks");
    }
  }

  async function getAll() {
    let offset = 0;
    let next: string;
    let tracks: SavedTrack[] = []

    let {update, stop} = Indicator.addLoading("Loading Tracks");
    do {
      const data = await makeRequest((api) => api.getMySavedTracks({ limit: 50, offset }));
      offset += data.limit;
		  next = data.next;
      tracks = [...tracks, ...data.items.map((d) => savedTrack(d))];
      update(tracks.length, data.total);
    } while(next)

    stop();
    Indicator.addAnnouncement("Tracks Loaded");
    return tracks;
  }

  async function fillStore(ts: SavedTrack[]) {
    trackStore.set({
      tracks: ts,      
      lastUpdated: new Date(Date.now()),
      status: State.Ready
    })
  }

}
