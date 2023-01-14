import { ExternalUrl } from "./ExternalUrl";
import { ResumePoint } from "./ResumePoint";
import { Show } from "./Show";
import { SpotifyImage } from "./SpotifyImage";

export class Episode {
  // Episode Full
  show?: Show;

  // Episode Simplified
  audio_preview_url!: string | null;
  description!: string;
  duration_ms!: number;
  explicit!: boolean;
  external_urls!: ExternalUrl;
  href!: string;
  id!: string;
  images!: SpotifyImage[];
  is_externally_hosted!: boolean;
  is_playable!: boolean;
  languages!: string[];
  name!: string;
  release_date!: string;
  release_date_precision!: string;
  resume_point!: ResumePoint;
  type!: 'episode';
  uri!: string;

  private constructor() {
  }

  public static fromFullEpisode(e: SpotifyApi.EpisodeObjectFull): Episode {
    const episode = Episode.fromSimplifiedEpisode(e);
    episode.show = Show.fromSimplifiedShow(e.show);
    return episode;
  }
  
  public static fromSimplifiedEpisode(e: SpotifyApi.EpisodeObjectSimplified): Episode {
    const episode = new Episode();
    episode.audio_preview_url = e.audio_preview_url;
    episode.description = e.description;
    episode.duration_ms = e.duration_ms;
    episode.explicit = e.explicit;
    episode.external_urls = ExternalUrl.fromExternalUrlObject(e.external_urls);
    episode.href = e.href;
    episode.id = e.id;
    episode.images = e.images.map(i => SpotifyImage.fromImageObject(i));
    episode.is_externally_hosted = e.is_externally_hosted;
    episode.is_playable = e.is_playable;
    episode.languages = e.languages;
    episode.name = e.name;
    episode.release_date = e.release_date;
    episode.release_date_precision = e.release_date_precision;
    episode.resume_point = ResumePoint.fromResumePointObject(e.resume_point);
    episode.type = e.type;
    episode.uri = e.uri;
    return episode;
  }
}