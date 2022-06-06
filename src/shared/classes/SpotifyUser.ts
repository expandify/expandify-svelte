import {ExplicitContent} from "./ExplicitContent";
import {ExternalUrls} from "./ExternalUrls";
import {Followers} from "./Followers";
import {Image} from "./Image";

export class SpotifyUser {
  country: string
  display_name: string
  email: string
  explicit_content: ExplicitContent = new ExplicitContent()
  external_urls: ExternalUrls = new ExternalUrls()
  followers: Followers = new Followers()
  href: string
  id: string
  images: Image[] = []
  product: string
  type: string
  uri: string

  static from(json){
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
    spotifyUser.images = json?.images?.map(value => Image.from(value))
    spotifyUser.product = json?.product
    spotifyUser.type = json?.type
    spotifyUser.uri = json?.uri
    
    return spotifyUser
  }
}


