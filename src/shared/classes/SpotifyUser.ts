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

  static from(json: any){
    if (json === null) return null
    
    let spotifyUser = new SpotifyUser()
    
    spotifyUser.country = json?.country
    spotifyUser.display_name = json?.display_name
    spotifyUser.email = json?.email
    spotifyUser.explicit_content = ExplicitContent.from(json?.explicit_content)
    spotifyUser.external_urls = ExternalUrls.from(json?.external_urls)
    spotifyUser.followers = Followers.from(json?.followers)
    spotifyUser.href = json?.href
    spotifyUser.id = json?.id
    spotifyUser.images = json?.images?.map((value: any) => Image.from(value))
    spotifyUser.product = json?.product
    spotifyUser.type = json?.type
    spotifyUser.uri = json?.uri
    
    return spotifyUser
  }
}


