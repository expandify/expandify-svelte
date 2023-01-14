import { ExternalUrl } from "./ExternalUrl";

export class TrackLink {
  external_urls!: ExternalUrl;
  href!: string;
  id!: string;
  type!: 'track';
  uri!: string;

  private constructor() {
  }

  public static fromTrackLinkObject(tl: SpotifyApi.TrackLinkObject): TrackLink {
    const trackLink = new TrackLink();
    trackLink.external_urls = ExternalUrl.fromExternalUrlObject(tl.external_urls);
    trackLink.href = tl.href;
    trackLink.id = tl.id;
    trackLink.type = tl.type;
    trackLink.uri = tl.uri;
    return trackLink;
  }
}