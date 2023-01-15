import { ExternalUrl } from './ExternalUrl';
import { Followers } from './Followers';
import { SpotifyImage } from './SpotifyImage';

export class Artist implements SpotifyData {
	// Artist Full
	followers?: Followers;
	genres?: string[];
	images?: SpotifyImage[];
	popularity?: number;

	// Artist Simplified
	external_urls!: ExternalUrl;
	href!: string;
	id!: string;
	name!: string;
	type!: 'artist';
	uri!: string;

	private constructor() {}

	public static fromFullArtist(full: SpotifyApi.ArtistObjectFull): Artist {
		const artist = Artist.fromSimplifiedArtist(full);
		artist.followers = Followers.fromFollowersObject(full.followers);
		artist.genres = full.genres;
		artist.images = full.images.map((i) => SpotifyImage.fromImageObject(i));
		artist.popularity = full.popularity;
		return artist;
	}

	public static fromSimplifiedArtist(simplified: SpotifyApi.ArtistObjectSimplified): Artist {
		const artist = new Artist();

		artist.external_urls = ExternalUrl.fromExternalUrlObject(simplified.external_urls);
		artist.href = simplified.href;
		artist.id = simplified.id;
		artist.name = simplified.name;
		artist.type = simplified.type;
		artist.uri = simplified.uri;
		return artist;
	}
}
