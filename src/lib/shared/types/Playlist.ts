import type {ExternalUrls} from "./ExternalUrls";
import type {Followers} from "./Followers";
import type {Image} from "./Image";

export interface Playlist {
  collaborative?: boolean
  description?: string | null
  external_urls?: ExternalUrls
  followers?: Followers
  href: string
  id: string
  images?: Image[]
  name: string
  owner: { id: string, name: string }
  public?: boolean
  snapshot_id: string
  tracks: { id: string, name: string }[]
  type?: string
  uri?: string
}