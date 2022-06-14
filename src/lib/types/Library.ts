import type {LibraryAlbum} from "./Album";
import type {LibraryTrack} from "./Track";
import type {LibraryArtist} from "./Artist";
import type {LibraryPlaylist} from "./Playlist";

export enum LibraryStatus {
  loading = 202,
  error = 500,
  ready = 200
}

export enum LibraryType {
  current = "current",
  snapshot = "snapshot"
}


export class LibraryItem<I> {
  status: LibraryStatus
  last_updated: string | null
  item: I

  constructor(status: LibraryStatus, last_updated: string | null, item: I) {
    this.status = status
    this.last_updated = last_updated
    this.item = item
  }
}

export class Library {
  saved_albums: LibraryItem<LibraryAlbum[]> = new LibraryItem(LibraryStatus.ready, null, [])
  followed_artists: LibraryItem<LibraryArtist[]> = new LibraryItem(LibraryStatus.ready, null, [])
  saved_tracks: LibraryItem<LibraryTrack[]> = new LibraryItem(LibraryStatus.ready, null, [])
  playlists: LibraryItem<LibraryPlaylist[]> = new LibraryItem(LibraryStatus.ready, null, [])
  owner: LibraryItem<string>
  type: LibraryType
  date: string

  constructor(owner: LibraryItem<string>, type: LibraryType, date: string) {
    this.owner = owner
    this.type = type
    this.date = date
  }

  public static clone(library: Library, newType: LibraryType = LibraryType.snapshot, newDate: boolean = true) {
    const date = newDate ? new Date().toString() : library.date
    const newLibrary = new Library(library.owner, newType, date)
    newLibrary.saved_albums = library.saved_albums
    newLibrary.followed_artists = library.followed_artists
    newLibrary.playlists = library.playlists
    newLibrary.saved_tracks = library.saved_tracks
    return newLibrary
  }
}

export class LibrarySimplified {
  albums: number
  artists: number
  tracks: number
  playlists: number
  owner: string
  type: LibraryType
  date: string
  id: string

  constructor(library: Library, id: string) {
    this.albums = library.saved_albums.item.length
    this.artists = library.followed_artists.item.length
    this.tracks = library.saved_tracks.item.length
    this.playlists = library.playlists.item.length
    this.owner = library.owner.item
    this.type = library.type
    this.date = library.date
    this.id = id
  }

}

