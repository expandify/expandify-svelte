import {writable, get} from "svelte/store";
import {browser} from "$app/env";

const API_ENDPOINT = import.meta.env.VITE_HOST_NAME + import.meta.env.VITE_API_ENDPOINT

export const STORE_STATUS = {
  INIT: "initializing",
  FETCHING: "fetching",
  FINISHED: "finished"
}

export function buildStore(name, defaults = {}) {
  let storeValue
  if (Object.keys(defaults).length === 0) {
    storeValue = {
      total: 0,
      items: [],
      status: STORE_STATUS.INIT
    }
  } else {
    storeValue = defaults
  }

  const store = writable(storeValue)

  if (browser) {
    // If we are in a browser, check if the localStorage already has items
    const storedItems = localStorage.getItem(name)

    if (storedItems) {
      try {
        // Needs to be parsed, since local storage can only handle strings
        store.set(JSON.parse(storedItems))
      } catch (err) {
        store.set(storeValue)
      }
    }
  }

  // Whenever a change on the store happens, it will be persisted into localStorage
  store.subscribe(value => browser && localStorage.setItem(name, JSON.stringify(value)))

  return store
}


export async function fetchPagedItems(store, apiPath, limit = 10) {

  console.log("fetcch Items ", apiPath)
  if (get(store).status === STORE_STATUS.FINISHED) {
    return
  }
  let next = null
  let offset = 0
  do {
    const res = await fetch(`${API_ENDPOINT}${apiPath}?offset=${offset}&limit=${limit}`)
    const data = await res.json()

    store.update(curr => {
      const newItems = curr.items
      newItems.push.apply(newItems, data.items)
      return ({total: data.total, items: newItems, status: STORE_STATUS.FETCHING});
    })
    offset = offset + limit
    next = data.next
  } while (next != null)

  store.update(curr => {
    return ({total: curr.total, items: curr.items, status: STORE_STATUS.FINISHED});
  })
}
