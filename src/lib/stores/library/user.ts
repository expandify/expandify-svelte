import { makeRequest } from "$lib/spotify/api";
import { userPrivate } from "$lib/spotify/converter";
import { writable } from "svelte/store";
import { StoreState } from "$lib/stores/types";


type UserStore = {  
  user: UserPrivate | null;
  lastUpdated: Date | null;
  status: StoreState
}

export const userStore = writable<UserStore>({
  user: null,
  lastUpdated: null,
  status: StoreState.Uninitialized
})

function upadteStatus(status: StoreState) {
  userStore.update((s) => ({...s, status: status}))
}


export module User {
  

  export async function laod() {
    try {
      upadteStatus(StoreState.Loading);

      const u = userPrivate(await makeRequest((api) => api.getMe()));  

      userStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}))
      userStore.update((s) => ({...s, user: u}))
      upadteStatus(StoreState.Ready);
    } catch (error) {
      upadteStatus(StoreState.Error);
    }
  }

}
