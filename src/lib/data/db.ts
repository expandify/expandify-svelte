import type { Album, Artist, Playlist, SavedTrack, UserPrivate } from '$lib/types/spotify';
import Dexie, { type Table } from 'dexie';

export class MySubClassedDexie extends Dexie {  
  albums!: Table<Album>; 
  artists!: Table<Artist>; 
  playlists!: Table<Playlist>; 
  tracks!: Table<SavedTrack>; 
  user!: Table<UserPrivate>; 

  constructor() {
    super('expandify');
    this.version(1).stores({
      albums: '++id', // Primary key and indexed props
      artists: '++id', 
      playlists: '++id', 
      tracks: '++id', 
      user: '++id' 
    });
  }
}

export const db = new MySubClassedDexie();
