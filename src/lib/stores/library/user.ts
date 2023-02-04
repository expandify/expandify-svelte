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

export function clearUser() {
  userStore.set({
    user: null,
    lastUpdated: null,
    status: StoreState.Uninitialized
  });
}

export function updateStatus(status: StoreState) {
  userStore.update((s) => ({...s, status: status}))
}

export function setUser(user: UserPrivate) {
  userStore.update((s) => ({...s, user: user}))
}

export function setUpdatedNow() {
  userStore.update((s) => ({...s, lastUpdated: new Date(Date.now())}));
}
