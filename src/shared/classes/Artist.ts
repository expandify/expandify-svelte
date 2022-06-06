import {ExternalUrls} from "./ExternalUrls";
import {Followers} from "./Followers";
import {Image} from "./Image";

export class Artist {
  external_urls: ExternalUrls = new ExternalUrls()
  followers: Followers = new Followers()
  genres: string[]
  href: string
  id: string
  images: Image[] = []
  name: string
  popularity: number
  type: string
  uri: string

  static from(json){
    if (json === null) return null

    let artist = new Artist()

    artist.external_urls = ExternalUrls.from(json?.external_urls)
    artist.followers = Followers.from(json?.followers)
    artist.genres = json?.genres
    artist.href = json?.href
    artist.id = json?.id
    artist.images = json?.images?.map(value => Image.from(value))
    artist.name = json?.name
    artist.popularity = json?.popularity
    artist.type = json?.type
    artist.uri = json?.uri

    return artist
  }
}