import {writable} from "svelte/store";
import {Library} from "../../shared/classes/Library";
import type {Album} from "../../shared/classes/Album";
import type {Artist} from "../../shared/classes/Artist";
import type {Playlist} from "../../shared/classes/Playlist";
import type {Track} from "../../shared/classes/Track";

const albumStore = writable<{state: number, albums: Album[]}>({state: Library.Status.ready, albums: [] })

const artistStore = writable<{state: number, artists: Artist[]}>({state: Library.Status.ready, artists: []})

const playlistStore = writable<{state: number, playlists: Playlist[]}>({state: Library.Status.ready, playlists: []})

const songStore = writable<{state: number, songs: Track[]}>({state: Library.Status.ready, songs: []})


export {albumStore, artistStore, playlistStore, songStore}