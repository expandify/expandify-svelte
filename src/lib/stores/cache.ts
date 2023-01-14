import { browser } from '$app/environment';
import type { Album } from '$lib/classes/Album';
import type { Artist } from '$lib/classes/Artist';
import type { Playlist } from '$lib/classes/Playlist';
import type { Track } from '$lib/classes/Track';
import { writable } from 'svelte/store'


/**
 * Caches the users albums.
 */
export const albumCache = writable<Album[]>([]);

/**
 * Caches the users albums.
 */
export const artistCache = writable<Artist[]>([]);

/**
 * Caches the users albums.
 */
export const playlistCache = writable<Playlist[]>([]);

/**
 * Caches the users albums.
 */
export const trackCache = writable<Track[]>([]);

/**
 * Clears the cache in the stores and the sessionStorage
 */
export function clearCache() {
  albumCache.set([])
  artistCache.set([])
  playlistCache.set([])
  trackCache.set([])

  window.sessionStorage.removeItem("albumCache")
  window.sessionStorage.removeItem("artistCache")
  window.sessionStorage.removeItem("playlistCache")
  window.sessionStorage.removeItem("trackCache")  
}



if (browser){
  let albums = window.sessionStorage.getItem("albumCache");
  let artists = window.sessionStorage.getItem("artistCache");
  let playlists = window.sessionStorage.getItem("playlistCache");
  let tracks = window.sessionStorage.getItem("trackCache");

  try {
    albumCache.set(JSON.parse(albums ?? "[]"));
  } catch (error) {
    window.sessionStorage.removeItem("albumCache");
  }

  try {
    artistCache.set(JSON.parse(artists ?? "[]"));
  } catch (error) {
    window.sessionStorage.removeItem("artistCache");
  }

  try {
    playlistCache.set(JSON.parse(playlists ?? "[]"));
  } catch (error) {
    window.sessionStorage.removeItem("playlistCache");
  }

  try {
    trackCache.set(JSON.parse(tracks ?? "[]"));
  } catch (error) {
    window.sessionStorage.removeItem("trackCache");
  }

  albumCache.subscribe((value) => window.sessionStorage.setItem("albumCache", JSON.stringify(value)));
  artistCache.subscribe((value) => window.sessionStorage.setItem("artistCache", JSON.stringify(value)));
  playlistCache.subscribe((value) => window.sessionStorage.setItem("playlistCache", JSON.stringify(value)));
  trackCache.subscribe((value) => window.sessionStorage.setItem("trackCache", JSON.stringify(value)));
}