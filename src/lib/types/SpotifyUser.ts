import type {ExplicitContent} from "./ExplicitContent";
import type {ExternalUrls} from "./ExternalUrls";
import type {Followers} from "./Followers";
import type {Image} from "./Image";

export interface SpotifyUser {
  country?: string
  display_name?: string
  email?: string
  explicit_content?: ExplicitContent
  external_urls?: ExternalUrls
  followers?: Followers | undefined
  href: string
  id: string
  images?: Image[]
  product?: string
  type?: string
  uri?: string

}
