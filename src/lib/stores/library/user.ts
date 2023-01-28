
import { makeRequest } from "$lib/spotify/api";
import { userPrivate } from "$lib/spotify/converter";
import { writable } from "svelte/store";
import { Indicator } from "../indicators";

const enum State {
  Uninitialized = 'Uninitialized',
  Ready = 'Ready',
  Error = 'Error',  
}

type UserStore = {  

  user: UserPrivate | null;
  
  lastUpdated: Date | null;
  status: State
}

export const userStore = writable<UserStore>({
  user: null,
  lastUpdated: null,
  status: State.Uninitialized
})


export module User {
  
  export async function laod() {
    try {
      userStore.update((s) => ({...s, status: State.Uninitialized}))
      const u = userPrivate(await makeRequest((api) => api.getMe()));  
      await fillStore(u);
      Indicator.addSuccess("User Data loaded")
    } catch (error) {
      userStore.update((s) => ({...s, status: State.Error}))
      Indicator.addError("Error Loading User Data");
    }
  }

  async function fillStore(u: UserPrivate) {
    userStore.set({
      user: u,      
      lastUpdated: new Date(Date.now()),
      status: State.Ready
    })
  }

}
