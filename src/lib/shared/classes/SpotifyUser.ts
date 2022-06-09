import {ExplicitContent} from "./ExplicitContent";
import {ExternalUrls} from "./ExternalUrls";
import {Followers} from "./Followers";
import {Image} from "./Image";

export class SpotifyUser {
  country: string | null = null
  display_name: string | null = null
  email: string | null = null
  explicit_content: ExplicitContent | null = new ExplicitContent()
  external_urls: ExternalUrls | null = new ExternalUrls()
  followers: Followers | null = new Followers()
  href: string | null = null
  id: string | null = null
  images: Image[] = []
  product: string | null = null
  type: string | null = null
  uri: string | null = null

  constructor(obj: any) {

    if (obj === null) throw new Error("Object is null")

    this.country = obj?.country
    this.display_name = obj?.display_name
    this.email = obj?.email
    this.explicit_content = ExplicitContent.from(obj?.explicit_content)
    this.external_urls = ExternalUrls.from(obj?.external_urls)
    this.followers = Followers.from(obj?.followers)
    this.href = obj?.href
    this.id = obj?.id
    this.images = obj?.images?.map((value: any) => Image.from(value))
    this.product = obj?.product
    this.type = obj?.type
    this.uri = obj?.uri
  }
}


