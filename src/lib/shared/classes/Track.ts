import {Album} from "./Album";
import {Artist} from "./Artist";
import {ExternalIds} from "./ExternalIds";
import {ExternalUrls} from "./ExternalUrls";
import {Restrictions} from "./Restrictions";

export class Track {
  album: Album | null = new Album()
  artists: Artist[] = []
  available_markets: string[] | null = null
  disc_number: number | null = null
  duration_ms: number | null = null
  explicit: boolean | null = null
  external_ids: ExternalIds | null = new ExternalIds()
  external_urls: ExternalUrls | null = new ExternalUrls()
  href: string | null = null
  id: string | null = null
  is_playable: boolean | null = null
  restrictions: Restrictions | null = new Restrictions()
  name: string | null = null
  popularity: number | null = null
  preview_url: string | null = null
  track_number: number | null = null
  type: string | null = null
  uri: string | null = null
  is_local: string | null = null
  added_at: string | null = null

  static from(json: any){
    if (json === null) return null
    
    let track = new Track()
    
    track.album = Album.from(json?.album)
    track.artists = json?.artists?.map((value: any) => Artist.from(value))
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
    track.added_at = json?.added_at
    
    return track
  }
}