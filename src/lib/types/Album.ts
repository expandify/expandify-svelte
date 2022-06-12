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
  uri: string;
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
}

export function toAlbum(album: SpotifyApi.SavedAlbumObject): Album {
  return {
    album_type: album.album.album_type,
    total_tracks: album.album.total_tracks,
    available_markets: album.album.available_markets,
    external_urls: album.album.external_urls,
    href: album.album.href,
    id: album.album.id,
    images: album.album.images,
    label: album.album.label,
    name: album.album.name,
    release_date: album.album.release_date,
    release_date_precision: album.album.release_date_precision,
    restrictions: album.album.restrictions,
    type: album.album.type,
    artists: album.album.artists.map(a => ({id: a.id, name: a.name})),
    tracks: [],
    genres: album.album.genres,
    popularity: album.album.popularity,
    uri: album.album.uri
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
    added_at: album.added_at
  }
}
