import {ExternalUrls} from "./ExternalUrls";
import {Image} from "./Image";
import {Restrictions} from "./Restrictions";
import {Artist} from "./Artist";
import {Track} from "./Track";
import {Paging} from "./Paging";

export class Album {
  album_type: string
  total_tracks: number
  available_markets: string[]
  external_urls: ExternalUrls = new ExternalUrls()
  href: string
  id: string
  images: Image[] = []
  name: string
  release_date: string
  release_date_precision: string
  restrictions: Restrictions = new Restrictions()
  type: string
  artists: Artist[] = []
  tracks: Paging<Track> = new Paging<Track>()

  static from(json){
    if (json === null) return null

    let album = new Album()

    album.album_type = json?.album_type
    album.total_tracks = json?.total_tracks
    album.available_markets = json?.available_markets
    album.external_urls = ExternalUrls.from(json?.external_urls)
    album.href = json?.href
    album.id = json?.id
    album.images = json?.images?.map(value => Image.from(value))
    album.name = json?.name
    album.release_date = json?.release_date
    album.release_date_precision = json?.release_date_precision
    album.restrictions = Restrictions.from(json?.restrictions)
    album.type = json?.type
    album.artists = json?.artists?.map(value => Artist.from(value))
    album.tracks = Paging.from<Track>(json?.tracks, Track.from)

    return album
  }
}