enum Status {
  loading = 202,
  error = 500,
  ready = 200
}

enum Type {
  current = "current",
  snapshot = "snapshot"
}


export class LibraryItem<I> {
  status: Status = Status.ready
  last_updated: string | null = null
  item: I | null = null

  constructor(status: Status = Status.ready, last_updated: string | null = null, item: I | null = null) {
    this.status = status;
    this.last_updated = last_updated;
    this.item = item;
  }
}

export interface LibraryTrack{
  id: string;
  added_at: string;
}

export class Library {

  static readonly Status = Status;
  static readonly Type = Type;

  saved_albums: LibraryItem<string[]> = new LibraryItem<string[]>()
  followed_artists: LibraryItem<string[]> = new LibraryItem<string[]>()
  saved_tracks: LibraryItem<LibraryTrack[]> = new LibraryItem<LibraryTrack[]>()
  playlists: LibraryItem<string[]> = new LibraryItem<string[]>()
  owner: LibraryItem<string> = new LibraryItem<string>()

  type: Type | null = null
  id: string | null = null
}

