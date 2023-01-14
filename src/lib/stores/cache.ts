import { browser } from '$app/environment';
import type { Album } from '$lib/classes/Album';
import type { Artist } from '$lib/classes/Artist';
import type { Playlist } from '$lib/classes/Playlist';
import type { Track } from '$lib/classes/Track';
import { get, writable, type Writable } from 'svelte/store'


const ALBUM_CACHE_NAME = "albumCache";
const ARTIST_CACHE_NAME = "artistCache";
const PLAYLIST_CACHE_NAME = "playlistCache";
const TRACK_CACHE_NAME = "trackCache";



export namespace Cache {

  
  export declare interface Data<T extends SpotifyData> {
    // The name of the cache
    name: string

    // Indicates the when the Cache was fully updated the last time.
    lastFullUpdate: Date | null;

    // The state of the Cache.
    state: State;

    // Total items that will be in the cache, when on state "finished".
    totalItems: number | null;

    // Amount of items that are currently loaded, but not in "items" yet.
    loadedItems: number | null;

    // The items of the Cache.
    items: T[];
  }

  export const enum State {
    // Indicates an empty cache    
    Empty = "Empty",
    // Indicates that the cache is currently loading data
    Loading = "Loading",
    // Indicates that the cache is currently loading secondary data (e.g. tracks of a playlist)
    LoadingSecondary = "LoadingSecondary",
    // The cache is finished and ready to use
    Finished = "Finished",
  }

    /**
   * Caches the users albums.
   */
  export const albums = writable<Data<Album>>(createEmpty(ALBUM_CACHE_NAME));

  /**
   * Caches the users albums.
   */
  export const artists = writable<Data<Artist>>(createEmpty(ARTIST_CACHE_NAME));

  /**
   * Caches the users albums.
   */
  export const playlists = writable<Data<Playlist>>(createEmpty(PLAYLIST_CACHE_NAME));

  /**
   * Caches the users albums.
   */
  export const tracks = writable<Data<Track>>(createEmpty(TRACK_CACHE_NAME));


  export function createEmpty<T extends SpotifyData>(name: string): Data<T> {
    return {name: name, state: State.Empty, lastFullUpdate: null, totalItems: null, items: [], loadedItems: null}
  }
  
  export function setState<T extends SpotifyData>(cache: Writable<Data<T>>, state: State) {
    if (get(cache).state === state) return;
    cache.update(data => ({...data, state: state}));
  }
  
  export function setTotalItems<T extends SpotifyData>(cache: Writable<Data<T>>, totalItems: number) {
    if (get(cache).totalItems === totalItems) return;
    cache.update(data => ({...data, totalItems: totalItems}));
  }

  export function setLoadedItems<T extends SpotifyData>(cache: Writable<Data<T>>, loadedItems: number) {
    if (get(cache).loadedItems === loadedItems) return;
    cache.update(data => ({...data, loadedItems: loadedItems}));
  }
  
  export function setLastUpdateNow<T extends SpotifyData>(cache: Writable<Data<T>>) {
    cache.update(data => ({...data, lastFullUpdate: new Date(Date.now())}));
  }
  
  export function setItems<T extends SpotifyData>(cache: Writable<Data<T>>, items: T[]) {
    cache.update(data => ({...data, items: items}));
  }
  
  export function addItems<T extends SpotifyData>(cache: Writable<Data<T>>, items: T[]) {
    cache.update(data => ({...data, items: [...data.items, ...items]}));
  }
  
  export function addItem<T extends SpotifyData>(cache: Writable<Data<T>>, item: T) {
    cache.update(data => ({...data, items: [...data.items, item]}));
  }

  export function clear<T extends SpotifyData>(cache: Writable<Data<T>>) {
    const name = get(cache).name;
    cache.set(createEmpty(name));
    window.sessionStorage.removeItem(name);
  }

  /**
   * Clears the cache in the stores and the sessionStorage
   */
  export function clearAll() {
    clear(albums);
    clear(artists);
    clear(playlists);
    clear(tracks);    
  }

}

export const albumCache = Cache.albums;
export const artistCache = Cache.artists;
export const playlistCache = Cache.playlists;
export const trackCache = Cache.tracks;



if (browser){

  let albums = window.sessionStorage.getItem(ALBUM_CACHE_NAME);
  let artists = window.sessionStorage.getItem(ARTIST_CACHE_NAME);
  let playlists = window.sessionStorage.getItem(PLAYLIST_CACHE_NAME);
  let tracks = window.sessionStorage.getItem(TRACK_CACHE_NAME);

  try {
    Cache.albums.set(JSON.parse(albums ?? ""));
  } catch (error) {
    Cache.clear(albumCache);
  }

  try {
    Cache.artists.set(JSON.parse(artists ?? ""));
  } catch (error) {
    Cache.clear(artistCache);
  }

  try {
    Cache.playlists.set(JSON.parse(playlists ?? ""));
  } catch (error) {
    Cache.clear(playlistCache);
  }

  try {
    Cache.tracks.set(JSON.parse(tracks ?? ""));
  } catch (error) {
    Cache.clear(trackCache);
  }

  //Cache.albums.subscribe((value) => window.sessionStorage.setItem(ALBUM_CACHE_NAME, JSON.stringify(value)));
  //Cache.artists.subscribe((value) => window.sessionStorage.setItem(ARTIST_CACHE_NAME, JSON.stringify(value)));
  //Cache.playlists.subscribe((value) => window.sessionStorage.setItem(PLAYLIST_CACHE_NAME, JSON.stringify(value)));
  //Cache.tracks.subscribe((value) => window.sessionStorage.setItem(TRACK_CACHE_NAME, JSON.stringify(value)));
}