import {browser} from "$app/env";
import {fetchPagedItems, fetchItems, fetchCursorItems} from "../api-connection/request.js";
import {get} from "svelte/store";
import {STORE_STATUS, updateStoreItems, updateStoreStatus} from "../../../stores/library.js";


async function populateStore(store, apiPath, initPage = null, ca) {
  if (browser) {
    if (get(store).status !== STORE_STATUS.READY) {
      return
    }
    updateStoreStatus(store, STORE_STATUS.FETCHING)
    let data = await fetchItems(apiPath)
    updateStoreStatus(store, STORE_STATUS.FINISHED)
  }
}

async function populatePagedStore(store, apiPath, firstPage = null, limit = 50) {
  if (browser) {
    if (get(store).status !== STORE_STATUS.READY) {
      return
    }
    updateStoreStatus(store, STORE_STATUS.FETCHING)
    const transform = data => data.items
    await fetchPagedItems(apiPath, data => updateStoreItems(store, data), transform, limit, firstPage)
    updateStoreStatus(store, STORE_STATUS.FINISHED)
  }
}

async function populateCursorStore(store, apiPath, firstPage = null, transform = (data) => data,  limit = 50) {
  if (browser) {
    if (get(store).status !== STORE_STATUS.READY) {
      return
    }
    updateStoreStatus(store, STORE_STATUS.FETCHING)
    await fetchCursorItems(apiPath, data => updateStoreItems(store, data), transform, limit, firstPage)
    updateStoreStatus(store, STORE_STATUS.FINISHED)
  }
}


export {populatePagedStore, populateStore, populateCursorStore}