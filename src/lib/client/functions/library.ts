import {LibraryItem, LibraryStatus} from "../../types/Library";
import type {Writable} from "svelte/types/runtime/store";
import {get} from "svelte/store";
import {albumStore, artistStore, playlistStore, trackStore} from "../stores/library";

async function refreshCurrentLibrary(session: App.Session) {
  _lockAll()
  await _refreshAll(session)
  _getAll(session)
}

async function backupCurrentLibrary(session: App.Session) {
  _lockAll()
  await fetch(session.BASE_URL + "/libraries/current", {method: 'POST'})
  _getAll(session)
}

function _lockAll() {
  trackStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))
  albumStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))
  artistStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))
  playlistStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))
}

async function _refreshAll(session: App.Session) {
  await fetch(session.BASE_URL + "/libraries/current/tracks", {method: 'POST'})
  await fetch(session.BASE_URL + "/libraries/current/albums", {method: 'POST'})
  await fetch(session.BASE_URL + "/libraries/current/artists", {method: 'POST'})
  await fetch(session.BASE_URL + "/libraries/current/playlists", {method: 'POST'})
}

function _getAll(session: App.Session) {
  _checkRegularly(albumStore, `${session.BASE_URL}/libraries/current/albums/__data.json`)
  _checkRegularly(artistStore, `${session.BASE_URL}/libraries/current/artists/__data.json`)
  _checkRegularly(playlistStore, `${session.BASE_URL}/libraries/current/playlists/__data.json`)
  _checkRegularly(trackStore, `${session.BASE_URL}/libraries/current/tracks/__data.json`)
}

function _checkRegularly(store: Writable<LibraryItem<any>>, url: string) {
  const libraryItem = get(store)
  const interval = setInterval(async () => {
    const items = await fetch(url)
    libraryItem.status = items.status
    if (items.status !== LibraryStatus.loading) {
      clearInterval(interval)
      const json: { libraryItem: any} = (await items.json())
      store.set(json.libraryItem)
    }
  }, 1000)
}


export {refreshCurrentLibrary, backupCurrentLibrary}