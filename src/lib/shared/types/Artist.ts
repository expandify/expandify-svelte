import type {ExternalUrls} from "./ExternalUrls";
import type {Followers} from "./Followers";
import type {Image} from "./Image";

export interface Artist {
  external_urls?: ExternalUrls
  followers?: Followers
  genres?: string[]
  href: string
  id: string
  images?: Image[]
  name: string
  popularity?: number
  type?: string
  uri?: string
}