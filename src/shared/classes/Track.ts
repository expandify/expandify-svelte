import {Album} from "./Album";
import {Artist} from "./Artist";
import {ExternalIds} from "./ExternalIds";
import {ExternalUrls} from "./ExternalUrls";
import {Restrictions} from "./Restrictions";

export class Track {
  album: Album = new Album()
  artists: Artist[] = []
  available_markets: string[]
  disc_number: number
  duration_ms: number
  explicit: boolean
  external_ids: ExternalIds = new ExternalIds()
  external_urls: ExternalUrls = new ExternalUrls()
  href: string
  id: string
  is_playable: boolean
  restrictions: Restrictions = new Restrictions()
  name: string
  popularity: number
  preview_url: string
  track_number: number
  type: string
  uri: string
  is_local: string

  static from(json){
    if (json === null) return null
    
    let track = new Track()
    
    track.album = Album.from(json?.album)
    track.artists = json?.artists?.map(value => Artist.from(value))
    track.available_markets = json?.available_markets
    track.disc_number = json?.disc_number
    track.duration_ms = json?.duration_ms
    track.explicit = json?.explicit
    track.external_ids = ExternalIds.from(json?.external_ids)
    track.external_urls = ExternalUrls.from(json?.external_urls)
    track.href = json?.href
    track.id = json?.id
    track.is_playable = json?.is_playable
    track.restrictions = Restrictions.from(json?.restrictions)
    track.name = json?.name
    track.popularity = json?.popularity
    track.preview_url = json?.preview_url
    track.track_number = json?.track_number
    track.type = json?.type
    track.uri = json?.uri
    track.is_local = json?.is_local
    
    return track
  }
}