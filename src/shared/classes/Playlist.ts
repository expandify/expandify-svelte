import {ExternalUrls} from "./ExternalUrls";
import {Followers} from "./Followers";
import {Image} from "./Image";
import {SpotifyUser} from "./SpotifyUser";
import {Paging} from "./Paging";
import {Track} from "./Track";

export class Playlist {
  collaborative: boolean | null = null
  description: string | null = null
  external_urls: ExternalUrls | null = new ExternalUrls()
  followers: Followers | null = new Followers()
  href: string | null = null
  id: string | null = null
  images: Image[] | null = []
  name: string | null = null
  owner: SpotifyUser | null = new SpotifyUser()
  public: boolean | null = null
  snapshot_id: string | null = null
  tracks: Paging<Track> | null = new Paging<Track>()
  type: string | null = null
  uri: string | null = null

  static from(json: any){
    if (json === null) return null
    
    let playlist = new Playlist()

    playlist.collaborative = json?.collaborative
    playlist.description = json?.description
    playlist.external_urls = ExternalUrls.from(json?.external_urls)
    playlist.followers = Followers.from(json?.followers)
    playlist.href = json?.href
    playlist.id = json?.id
    playlist.images = json?.images?.map((value: any) => Image.from(value))
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