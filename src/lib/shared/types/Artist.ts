import type {ExternalUrls} from "./ExternalUrls";
import type {Followers} from "./Followers";
import type {Image} from "./Image";

export interface Artist {
  external_urls: ExternalUrls
  followers: Followers
  genres: string[]
  href: string
  id: string
  images?: Image[]
  name: string
  popularity: number
  type: "artist"
  uri: string
}

export interface LibraryArtist {
  artist: {
    id: string
    name: string
    images?: Image[]
  }
}

export function toArtist(artist: SpotifyApi.ArtistObjectFull): Artist {
  return {
    external_urls: artist.external_urls,
    followers: artist.followers,
    genres: artist.genres,
    href: artist.href,
    id: artist.id,
    images: artist.images,
    name: artist.name,
    popularity: artist.popularity,
    type: artist.type,
    uri: artist.uri
  }
}

export function toLibraryArtist(artist: SpotifyApi.ArtistObjectFull): LibraryArtist {
  return {
    artist: {
      id: artist.id,
      name: artist.name,
      images: artist.images
    }
  }
}
