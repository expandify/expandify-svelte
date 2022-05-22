import {writable, get} from "svelte/store";
import {browser} from "$app/env";
import {fetchPagedItems, fetchItems, fetchCursorItems} from "../api-connection/request.js";

const STORE_STATUS = {
  INIT: "initializing",
  FETCHING: "fetching",
  FINISHED: "finished"
}

function buildLibraryStore(name, defaults = {}) {
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


async function populateStore(store, apiPath, callback) {
  if (browser) {
    if (get(store).status === STORE_STATUS.FINISHED) {
      return
    }
    await fetchItems(apiPath, callback)
  }
}

async function populatePagedStore(store, apiPath, limit = 50) {
  if (browser) {
    if (get(store).status === STORE_STATUS.FINISHED) {
      return
    }
    await fetchPagedItems(apiPath, data => updateStoreItems(store, data), limit)
    updateStoreStatus(store, STORE_STATUS.FINISHED)
  }
}

async function populateCursorStore(store, apiPath, transform = data => data, limit = 50) {
  if (browser) {
    if (get(store).status === STORE_STATUS.FINISHED) {
      return
    }
    await fetchCursorItems(apiPath, data => updateStoreItems(store, data), transform, limit)
    updateStoreStatus(store, STORE_STATUS.FINISHED)
  }
}

function updateStoreItems(store, data) {
  store.update(curr => {
    const newItems = curr.items
    newItems.push.apply(newItems, data.items)
    return ({total: data.total, items: newItems, status: STORE_STATUS.FETCHING});
  })
}

function updateStoreStatus(store, status) {
  store.update(curr => {
    return ({total: curr.total, items: curr.items, status: status});
  })
}

export {buildLibraryStore, populatePagedStore, populateStore, populateCursorStore, STORE_STATUS}