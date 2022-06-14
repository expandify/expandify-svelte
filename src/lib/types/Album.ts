import type {ExternalUrls} from "./ExternalUrls";
import type {Image} from "./Image";
import type {Restrictions} from "./Restrictions";

export interface Album {
  album_type?: "album" | "single" | "compilation";
  total_tracks: number
  available_markets?: string[] | undefined
  external_urls: ExternalUrls
  href: string
  id: string
  images?: Image[]
  label?: string | undefined
  name: string
  release_date: string
  release_date_precision: "year" | "month" | "day"
  restrictions?: Restrictions | undefined
  type: "album"
  artists: {id: string, name: string}[]
  tracks: { id: string, name: string }[]
  genres?: string[] | undefined
  popularity?: number | undefined
  uri: string
  complete: boolean
}

export interface LibraryAlbum{
  album: {
    id: string,
    name: string,
    artists: {id: string, name: string}[]
    total_tracks: number
    images?: Image[]
  },
  added_at: string
  // This field is used when two libraries are compared, to indicate on which library the album is present.
  // Null indicates, it is present on both libraries
  library?: string | null
}

export function toAlbum(album: SpotifyApi.AlbumObjectFull): Album {
  return {
    album_type: album.album_type,
    total_tracks: album.total_tracks,
    available_markets: album.available_markets,
    external_urls: album.external_urls,
    href: album.href,
    id: album.id,
    images: album.images,
    label: album.label,
    name: album.name,
    release_date: album.release_date,
    release_date_precision: album.release_date_precision,
    restrictions: album.restrictions,
    type: album.type,
    artists: album.artists.map(a => ({id: a.id, name: a.name})),
    tracks: [],
    genres: album.genres,
    popularity: album.popularity,
    uri: album.uri,
    complete: false
  }
}

export function toLibraryAlbum(album: SpotifyApi.SavedAlbumObject): LibraryAlbum {
  return {
    album: {
      id: album.album.id,
      name: album.album.name,
      artists: album.album.artists.map(a => ({id: a.id, name: a.name})),
      total_tracks: album.album.total_tracks,
      images: album.album.images
    },
    added_at: album.added_at,
    library: null
  }
}
