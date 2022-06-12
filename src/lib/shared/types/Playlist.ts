import type {ExternalUrls} from "./ExternalUrls";
import type {Followers} from "./Followers";
import type {Image} from "./Image";

export interface Playlist {
  collaborative: boolean
  description: string | null
  external_urls: ExternalUrls
  followers?: Followers
  href: string
  id: string
  images: Image[]
  name: string
  owner: { id: string, name: string | undefined }
  public: boolean | null
  snapshot_id: string
  tracks: { id: string, name: string }[]
  type: "playlist"
  uri: string
}

export interface LibraryPlaylist{
  playlist: {
    id: string,
    name: string,
    owner: {id: string, name: string | undefined}
    images?: Image[]
  },
}

export function toPlaylist(playlist: SpotifyApi.PlaylistObjectSimplified): Playlist {
  return {
    collaborative: playlist.collaborative,
    description: playlist.description,
    external_urls: playlist.external_urls,
    href: playlist.href,
    id: playlist.id,
    images: playlist.images,
    name: playlist.name,
    owner: {id: playlist.owner.id, name: playlist.owner.display_name},
    public: playlist.public,
    snapshot_id: playlist.snapshot_id,
    tracks: [],
    type: playlist.type,
    uri: playlist.uri
  }
}

export function toLibraryPlaylist(playlist: SpotifyApi.PlaylistObjectSimplified): LibraryPlaylist {
  return {
    playlist: {
      id: playlist.id,
      name: playlist.name,
      owner: {id: playlist.owner.id, name: playlist.owner.display_name},
      images: playlist.images
    }
  }
}