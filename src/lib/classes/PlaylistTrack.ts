import { Episode } from "./Episode";
import { Track } from "./Track";
import { User } from "./User";

export class PlaylistTrack implements SpotifyData {
  added_at!: string;
  added_by!: User;
  is_local!: boolean;
  track!: Track | Episode;

  private constructor() {
  }

  public static fromPlaylistTrackObject(pt: SpotifyApi.PlaylistTrackObject): PlaylistTrack {
    const playlistTrack = new PlaylistTrack();
    playlistTrack.added_at = pt.added_at;
    playlistTrack.added_by = User.fromPublicUser(pt.added_by);
    playlistTrack.is_local = pt.is_local;

    if (pt.track.type === "track") {
      playlistTrack.track = Track.fromFullTrack(pt.track);
    }

    if (pt.track.type === "episode") {
      playlistTrack.track = Episode.fromFullEpisode(pt.track);
    }
    
    return playlistTrack;
  }
}