import {writable} from "svelte/store";
import {LibraryItem, LibrarySimplified, LibraryStatus, LibraryType} from "$lib/types/Library";
import type {LibraryArtist} from "$lib/types/Artist";
import type {LibraryPlaylist} from "$lib/types/Playlist";
import type {LibraryTrack} from "$lib/types/Track";
import type {LibraryAlbum} from "$lib/types/Album";

const albumStore = writable<LibraryItem<LibraryAlbum[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const artistStore = writable<LibraryItem<LibraryArtist[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const playlistStore = writable<LibraryItem<LibraryPlaylist[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const trackStore = writable<LibraryItem<LibraryTrack[]>>(new LibraryItem(LibraryStatus.ready, null, []))
const libraryStore = writable<{ activeLibrary: LibrarySimplified, compareTo: LibrarySimplified | null }>({
  activeLibrary: {
    id: LibraryType.current,
    type: LibraryType.current,
    date: new Date().toString(),
    owner: "",
    tracks: 0,
    albums: 0,
    artists: 0,
    playlists: 0
  },
  compareTo: null
})


export {albumStore, artistStore, playlistStore, trackStore, libraryStore}