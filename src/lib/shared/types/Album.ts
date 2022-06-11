import type {ExternalUrls} from "./ExternalUrls";
import type {Image} from "./Image";
import type {Restrictions} from "./Restrictions";

export class Album {
  album_type?: string
  total_tracks?: number
  available_markets?: string[]
  external_urls?: ExternalUrls
  href: string
  id: string
  images?: Image[]
  label?: string
  name: string
  release_date?: string
  release_date_precision?: string
  restrictions?: Restrictions
  type?: string
  artists: {id: string, name: string}[]
  tracks: { id: string, name: string }[]
  genres?: string[]
  popularity?: number

  constructor(obj: any) {

  }
}