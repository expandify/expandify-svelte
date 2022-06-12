import type {ExternalIds} from "./ExternalIds";
import type {ExternalUrls} from "./ExternalUrls";
import type {Restrictions} from "./Restrictions";
import type {Image} from "./Image";

export interface Track {
  album: { id: string, name: string, images: Image[] }
  artists: { id: string, name: string }[]
  available_markets?: string[] | undefined
  disc_number: number
  duration_ms: number
  explicit: boolean
  external_ids: ExternalIds
  external_urls: ExternalUrls
  href: string
  id: string
  is_playable?: boolean | undefined
  restrictions?: Restrictions
  name: string
  popularity: number
  preview_url: string | null
  track_number: number
  type: "track"
  uri: string
  is_local?: boolean | undefined
}

export interface LibraryTrack{
  track: {
    name: string
    artists: { id: string, name: string }[]
    album: { id: string, name: string, images: Image[] }
    duration_ms: number
    id: string
  };
  added_at: string;
}

export function toTrack(track: SpotifyApi.SavedTrackObject): Track {
  return {
    album: {id: track.track.album.id, name: track.track.album.name, images: track.track.album.images},
    artists: track.track.artists.map(a => ({id: a.id, name: a.name})),
    available_markets: track.track.available_markets,
    disc_number: track.track.disc_number,
    duration_ms: track.track.duration_ms,
    explicit: track.track.explicit,
    external_ids: track.track.external_ids,
    external_urls: track.track.external_urls,
    href: track.track.href,
    id: track.track.id,
    is_playable: track.track.is_playable,
    restrictions: track.track.restrictions,
    name: track.track.name,
    popularity: track.track.popularity,
    preview_url: track.track.preview_url,
    track_number: track.track.track_number,
    type: track.track.type,
    uri: track.track.uri,
    is_local: track.track.is_local
  }
}

export function toLibraryTrack(track: SpotifyApi.SavedTrackObject): LibraryTrack {
  return {
    track: {
      name: track.track.name,
      album: {id: track.track.album.id, name: track.track.album.name, images: track.track.album.images},
      artists: track.track.artists.map(a => ({id: a.id, name: a.name})),
      duration_ms: track.track.duration_ms,
      id: track.track.id
    },
    added_at: track.added_at
  }
}