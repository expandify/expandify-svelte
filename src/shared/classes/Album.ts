import {ExternalUrls} from "./ExternalUrls";
import {Image} from "./Image";
import {Restrictions} from "./Restrictions";
import {Artist} from "./Artist";
import {Track} from "./Track";
import {Paging} from "./Paging";

export class Album {
  album_type: string | null = null
  total_tracks: number | null = null
  available_markets: string[] = []
  external_urls: ExternalUrls | null = new ExternalUrls()
  href: string | null = null
  id: string | null = null
  images: Image[] = []
  label: string | null = null
  name: string | null = null
  release_date: string | null = null
  release_date_precision: string | null = null
  restrictions: Restrictions | null = new Restrictions()
  type: string | null = null
  artists: Artist[] = []
  tracks: Paging<String> | null = new Paging<String>()

  static from(json: any){
    if (json === null) return null

    let album = new Album()

    album.album_type = json?.album_type
    album.total_tracks = json?.total_tracks
    album.available_markets = json?.available_markets
    album.external_urls = ExternalUrls.from(json?.external_urls)
    album.href = json?.href
    album.id = json?.id
    album.images = json?.images?.map((value: any) => Image.from(value))
    album.label = json?.label
    album.name = json?.name
    album.release_date = json?.release_date
    album.release_date_precision = json?.release_date_precision
    album.restrictions = Restrictions.from(json?.restrictions)
    album.type = json?.type
    album.artists = json?.artists?.map((value: any) => Artist.from(value))
    album.tracks = Paging.from<String>(json?.tracks, (track) => track.id)

    return album
  }
}