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
  last_updated?: string | null
  item: I | null

  constructor(status: LibraryStatus, last_updated: string | null, item: I | null) {
    this.status = status
    this.last_updated = last_updated
    this.item = item
  }
}

export interface LibraryTrack{
  id: string;
  added_at: string;
}

export class Library {
  saved_albums: LibraryItem<string[]> = new LibraryItem<string[]>(LibraryStatus.ready, null, [])
  followed_artists: LibraryItem<string[]> = new LibraryItem<string[]>(LibraryStatus.ready, null, [])
  saved_tracks: LibraryItem<LibraryTrack[]> = new LibraryItem<LibraryTrack[]>(LibraryStatus.ready, null, [])
  playlists: LibraryItem<string[]> = new LibraryItem<string[]>(LibraryStatus.ready, null, [])
  owner: LibraryItem<string>
  type: LibraryType

  constructor(owner: LibraryItem<string>, type: LibraryType) {
    this.owner = owner
    this.type = type
  }
}

