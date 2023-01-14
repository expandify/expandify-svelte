import { Copyright } from "./Copyright";
import { Episode } from "./Episode";
import { ExternalUrl } from "./ExternalUrl";
import { SpotifyImage } from "./SpotifyImage";

export class Show {
  // Show Full
  episodes?: Episode[];

  // Show Simplified
  available_markets?: string[];
  copyrights!: Copyright[];
  description!: string;
  explicit!: boolean;
  external_urls!: ExternalUrl;
  href!: string;
  id!: string;
  images!: SpotifyImage[];
  is_externally_hosted!: boolean;
  languages!: string[];
  media_type!: string;
  name!: string;
  publisher!: string;
  type!: 'show';
  uri!: string;

  private constructor() {
  }

  public static fromFullShow(s: SpotifyApi.ShowObjectFull): Show {
    const show = Show.fromSimplifiedShow(s);
    show.episodes = s.episodes.map(e => Episode.fromSimplifiedEpisode(e));
    return show;
  }

  public static fromSimplifiedShow(s: SpotifyApi.ShowObjectSimplified): Show {
    const show = new Show();

    show.available_markets = s.available_markets;
    show.copyrights = s.copyrights.map(c => Copyright.fromCopyrightObject(c));
    show.description = s.description;
    show.explicit = s.explicit;
    show.external_urls = ExternalUrl.fromExternalUrlObject(s.external_urls);
    show.href = s.href;
    show.id = s.id;
    show.images = s.images.map(i => SpotifyImage.fromImageObject(i));
    show.is_externally_hosted = s.is_externally_hosted;
    show.languages = s.languages;
    show.media_type = s.media_type;
    show.name = s.name;
    show.publisher = s.publisher;
    show.type = s.type;
    show.uri = s.uri;
  
    return show;
  }
}