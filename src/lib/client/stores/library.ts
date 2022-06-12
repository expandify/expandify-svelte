import {get, writable} from "svelte/store";
import {LibraryItem, LibraryStatus, LibraryType} from "../../shared/types/Library";
import type {LibraryArtist} from "../../shared/types/Artist";
import type {LibraryPlaylist} from "../../shared/types/Playlist";
import type {LibraryTrack} from "../../shared/types/Track";
import type {LibraryAlbum} from "../../shared/types/Album";
import type {Writable} from "svelte/types/runtime/store";

const albumStore = writable<LibraryItem<LibraryAlbum[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const artistStore = writable<LibraryItem<LibraryArtist[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const playlistStore = writable<LibraryItem<LibraryPlaylist[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const trackStore = writable<LibraryItem<LibraryTrack[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const libraryStore = writable<{currentLibrary: string}>({currentLibrary: LibraryType.current})


async function refreshCurrentLibrary(session: App.Session) {

  trackStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))
  albumStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))
  artistStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))
  playlistStore.update(item => new LibraryItem(LibraryStatus.loading, item.last_updated, item.item))

  await fetch(session.BASE_URL + "/library/current/tracks", {method: 'POST'})
  await fetch(session.BASE_URL + "/library/current/albums", {method: 'POST'})
  await fetch(session.BASE_URL + "/library/current/artists", {method: 'POST'})
  await fetch(session.BASE_URL + "/library/current/playlists", {method: 'POST'})

  _checkRegularly(albumStore, `${session.BASE_URL}/library/current/albums/__data.json`)
  _checkRegularly(artistStore, `${session.BASE_URL}/library/current/artists/__data.json`)
  _checkRegularly(playlistStore, `${session.BASE_URL}/library/current/playlists/__data.json`)
  _checkRegularly(trackStore, `${session.BASE_URL}/library/current/tracks/__data.json`)
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


export {albumStore, artistStore, playlistStore, trackStore, libraryStore, refreshCurrentLibrary}