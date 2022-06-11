import {writable} from "svelte/store";
import {LibraryStatus, LibraryType} from "../../shared/types/Library";
import type {Album} from "../../shared/types/Album";
import type {Artist} from "../../shared/types/Artist";
import type {Playlist} from "../../shared/types/Playlist";
import type {Track} from "../../shared/types/Track";

const albumStore = writable<{state: number, albums: Album[]}>({state: LibraryStatus.ready, albums: [] })

const artistStore = writable<{state: number, artists: Artist[]}>({state: LibraryStatus.ready, artists: []})

const playlistStore = writable<{state: number, playlists: Playlist[]}>({state: LibraryStatus.ready, playlists: []})

const trackStore = writable<{state: number, tracks: Track[]}>({state: LibraryStatus.ready, tracks: []})

const libraryStore = writable<{currentLibrary: string}>({currentLibrary: LibraryType.current})

export {albumStore, artistStore, playlistStore, trackStore, libraryStore}