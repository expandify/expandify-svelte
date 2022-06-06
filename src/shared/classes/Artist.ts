import {ExternalUrls} from "./ExternalUrls";
import {Followers} from "./Followers";
import {Image} from "./Image";

export class Artist {
  external_urls: ExternalUrls | null = new ExternalUrls()
  followers: Followers | null = new Followers()
  genres: string[] | null = null
  href: string | null = null
  id: string | null = null
  images: Image[] = []
  name: string | null = null
  popularity: number | null = null
  type: string | null = null
  uri: string | null = null

  static from(json: any){
    if (json === null) return null

    let artist = new Artist()

    artist.external_urls = ExternalUrls.from(json?.external_urls)
    artist.followers = Followers.from(json?.followers)
    artist.genres = json?.genres
    artist.href = json?.href
    artist.id = json?.id
    artist.images = json?.images?.map((value: any) => Image.from(value))
    artist.name = json?.name
    artist.popularity = json?.popularity
    artist.type = json?.type
    artist.uri = json?.uri

    return artist
  }
}