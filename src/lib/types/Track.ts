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
  is_local?: boolean | undefined,
  complete: boolean
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
  // This field is used when two libraries are compared, to indicate on which library the album is present.
  // Null indicates, it is present on both libraries
  library?: string | null
}

export function toTrack(track: SpotifyApi.TrackObjectFull): Track {
  return {
    album: {id: track.album.id, name: track.album.name, images: track.album.images},
    artists: track.artists.map(a => ({id: a.id, name: a.name})),
    available_markets: track.available_markets,
    disc_number: track.disc_number,
    duration_ms: track.duration_ms,
    explicit: track.explicit,
    external_ids: track.external_ids,
    external_urls: track.external_urls,
    href: track.href,
    id: track.id,
    is_playable: track.is_playable,
    restrictions: track.restrictions,
    name: track.name,
    popularity: track.popularity,
    preview_url: track.preview_url,
    track_number: track.track_number,
    type: track.type,
    uri: track.uri,
    is_local: track.is_local,
    complete: false
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
    added_at: track.added_at,
    library: null
  }
}