import type {Album} from "./Album";
import type {Artist} from "./Artist";
import type {Track} from "./Track";
import type {Playlist} from "./Playlist";
import type {SpotifyUser} from "./SpotifyUser";


enum Status {
  loading,
  error,
  ready
}

enum Type {
  current = "current",
  snapshot = "snapshot"
}


export class LibraryItem<Item> {
  status: Status | null = null
  last_updated: string | null = null
  item: Item | null = null

  constructor(status: Status | null = null, last_updated: string | null = null, item: Item | null = null) {
    this.status = status;
    this.last_updated = last_updated;
    this.item = item;
  }

}

export class Library {

  static readonly Status = Status;
  static readonly Type = Type;

  saved_albums: LibraryItem<String[]> = new LibraryItem<String[]>()
  followed_artists: LibraryItem<String[]> = new LibraryItem<String[]>()
  saved_tracks: LibraryItem<String[]> = new LibraryItem<String[]>()
  playlists: LibraryItem<String[]> = new LibraryItem<String[]>()
  owner: LibraryItem<String> = new LibraryItem<String>()

  type: Type | null = null
}

