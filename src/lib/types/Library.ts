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
  item: I | null

  constructor(status: LibraryStatus, last_updated: string | null, item: I | null) {
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

  constructor(owner: LibraryItem<string>, type: LibraryType) {
    this.owner = owner
    this.type = type
  }
}

