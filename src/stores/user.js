import {buildStore, STORE_STATUS} from "./builder.js";
import {get} from "svelte/store";

const API_ENDPOINT = import.meta.env.VITE_HOST_NAME + import.meta.env.VITE_API_ENDPOINT

const USER_DEFAULTS = {
  user: {},
  status: STORE_STATUS.INIT
}

export const user = buildStore("user", USER_DEFAULTS)

async function fetchUser() {
  if (get(user).status === STORE_STATUS.FINISHED) {
    return
  }

  const res = await fetch(`${API_ENDPOINT}/library/user`)
  const data = await res.json()

  user.set({user: data, status: STORE_STATUS.FINISHED})

}

fetchUser()