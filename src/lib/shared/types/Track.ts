import type {ExternalIds} from "./ExternalIds";
import type {ExternalUrls} from "./ExternalUrls";
import type {Restrictions} from "./Restrictions";
import type {Image} from "./Image";

export interface Track {
  album: { id: string, name: string, images: Image[] }
  artists: { id: string, name: string }[]
  available_markets?: string[]
  disc_number?: number
  duration_ms: number
  explicit?: boolean
  external_ids?: ExternalIds
  external_urls?: ExternalUrls
  href: string
  id: string
  is_playable?: boolean
  restrictions?: Restrictions
  name: string
  popularity?: number
  preview_url?: string
  track_number?: number
  type?: string
  uri?: string
  is_local?: string
  added_at?: string

}