import {writable} from "svelte/store";
import {browser} from "$app/env";
import {createCookieString} from "../shared/cookies.js";

const STORE_STATUS = {
  READY: "initializing",
  FETCHING: "fetching",
  FINISHED: "finished"
}

function buildLibraryStore(name, defaults = {}) {
  let storeValue
  if (Object.keys(defaults).length === 0) {
    storeValue = {
      total: 0,
      items: [],
      status: STORE_STATUS.READY
    }
  } else {
    storeValue = defaults
  }

  const store = writable(storeValue)

  if (browser) {
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
  store.subscribe(value => {
    if (browser) {
      localStorage.setItem(name, JSON.stringify(value))
      // if (value.status === STORE_STATUS.FINISHED) {
      //   document.cookie = createCookieString(name, "cached", false)
      // } else {
      //   document.cookie = createCookieString(name, "missing", false)
      // }
    }
  })

  return store
}

function emptyLibraryStore(store, defaults = null) {
  if (defaults != null) {
    store.set(defaults)
    return
  }

  store.set({
    total: 0,
    items: [],
    status: STORE_STATUS.READY
  })
}

function updateStoreItems(store, data) {
  store.update(curr => {
    const newItems = curr.items
    newItems.push.apply(newItems, data.items)
    return ({total: data.total, items: newItems, status: curr.status});
  })
}

function updateStoreStatus(store, status) {
  store.update(curr => {
    return ({total: curr.total, items: curr.items, status: status});
  })
}


const userStore = buildLibraryStore("user", {user: {}, status: STORE_STATUS.READY})
const albumStore = buildLibraryStore("albums")
const artistStore = buildLibraryStore("artists")
const playlistStore = buildLibraryStore("playlists")
const songStore = buildLibraryStore("songs")
const libraryStore = buildLibraryStore("libraries")

export {
  userStore,
  albumStore,
  artistStore,
  playlistStore,
  songStore,
  libraryStore,
  STORE_STATUS,
  emptyLibraryStore,
  updateStoreStatus,
  updateStoreItems
}