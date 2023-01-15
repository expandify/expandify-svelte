import { ExternalUrl } from './ExternalUrl';
import { Followers } from './Followers';
import { SpotifyImage } from './SpotifyImage';
import type { Track } from './Track';
import { User } from './User';

export class Playlist implements SpotifyData {
	// Playlist Full
	followers?: Followers;
	tracks: Track[] = [];

	// Playlist Simplified
	total_tracks?: number;
	collaborative!: boolean;
	description!: string | null;
	external_urls!: ExternalUrl;
	href!: string;
	id!: string;
	images!: SpotifyImage[];
	name!: string;
	owner!: User;
	public!: boolean;
	snapshot_id!: string;
	type!: 'playlist';
	uri!: string;

	private constructor() {}

	public static fromFullPlaylist(full: SpotifyApi.PlaylistObjectFull, tracks: Track[]): Playlist {
		const playlist = Playlist.fromSimplifiedPlaylist(full);
		playlist.followers = Followers.fromFollowersObject(full.followers);
		playlist.tracks = tracks;
		return playlist;
	}

	public static fromSimplifiedPlaylist(simplified: SpotifyApi.PlaylistObjectSimplified): Playlist {
		const playlist = new Playlist();

		playlist.total_tracks = simplified.tracks.total;
		playlist.collaborative = simplified.collaborative;
		playlist.description = simplified.description;
		playlist.external_urls = ExternalUrl.fromExternalUrlObject(simplified.external_urls);
		playlist.href = simplified.href;
		playlist.id = simplified.id;
		playlist.images = simplified.images.map((i) => SpotifyImage.fromImageObject(i));
		playlist.name = simplified.name;
		playlist.owner = User.fromPublicUser(simplified.owner);
		playlist.public = simplified.public;
		playlist.snapshot_id = simplified.snapshot_id;
		playlist.type = simplified.type;
		playlist.uri = simplified.uri;

		return playlist;
	}
}
