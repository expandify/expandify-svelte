import {ExternalUrls} from "./ExternalUrls";
import {Followers} from "./Followers";
import {Image} from "./Image";
import {SpotifyUser} from "./SpotifyUser";
import {Paging} from "./Paging";
import {Track} from "./Track";

export class Playlist {
  collaborative: boolean
  description: string | null
  external_urls: ExternalUrls = new ExternalUrls()
  followers: Followers = new Followers()
  href: string
  id: string
  images: Image[] = []
  name: string
  owner: SpotifyUser = new SpotifyUser()
  public: boolean
  snapshot_id: string
  tracks: Paging<Track> = new Paging<Track>()
  type: string
  uri: string

  static from(json){
    if (json === null) return null
    
    let playlist = new Playlist()

    playlist.collaborative = json?.collaborative
    playlist.description = json?.description
    playlist.external_urls = ExternalUrls.from(json?.external_urls)
    playlist.followers = Followers.from(json?.followers)
    playlist.href = json?.href
    playlist.id = json?.id
    playlist.images = json?.images?.map(value => Image.from(value))
    playlist.name = json?.name
    playlist.owner = SpotifyUser.from(json?.owner)
    playlist.public = json?.public
    playlist.snapshot_id = json?.snapshot_id
    playlist.tracks = Paging.from<Track>(json?.tracks, Track.from)
    playlist.type = json?.type
    playlist.uri = json?.uri

    return playlist
  }
}