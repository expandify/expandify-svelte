import type { Album, Artist, Playlist, SavedAlbum, SavedTrack, UserPrivate } from "./spotify"

export interface StoreError {
  message: string,
  cause: any
}

export interface LibraryStoreComponent {  
  loading: boolean,   
  updated: Date | null,
  error: StoreError | null
}

export interface AlbumStore extends LibraryStoreComponent {
  albums: SavedAlbum[],
  total: number,
}

export interface ArtistStore extends LibraryStoreComponent {
  artists: Artist[],
  total: number,
}

export interface PlaylistStore extends LibraryStoreComponent {
  playlists: Playlist[],
  total: number,
}

export interface TrackStore extends LibraryStoreComponent {
  tracks: SavedTrack[],
  total: number,
}

export interface UserStore extends LibraryStoreComponent {
  user: UserPrivate | null
}