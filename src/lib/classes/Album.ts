import { Artist } from './Artist';
import { Copyright } from './Copyright';
import { ExternalId } from './ExternalId';
import { ExternalUrl } from './ExternalUrl';
import { SpotifyImage } from './SpotifyImage';
import type { Track } from './Track';

export class Album implements SpotifyData {
	// Saved Album
	added_at?: string;

	// Album Full
	artists?: Artist[];
	copyrights?: Copyright[];
	external_ids?: ExternalId;
	genres?: string[];
	popularity?: number;
	release_date?: string;
	release_date_precision?: string;
	total_tracks?: number;
	tracks: Track[] = [];

	// Album Simplified
	album_type!: string;
	available_markets?: string[];
	external_urls!: ExternalUrl;
	href!: string;
	id!: string;
	images!: SpotifyImage[];
	name!: string;
	type!: 'album';
	uri!: string;

	private constructor() {}

	public static fromSavedAlbum(saved: SpotifyApi.SavedAlbumObject, tracks: Track[]): Album {
		const album = Album.fromFullAlbum(saved.album, tracks);
		album.added_at = saved.added_at;
		return album;
	}

	public static fromFullAlbum(full: SpotifyApi.AlbumObjectFull, tracks: Track[]): Album {
		const album = Album.fromSimplifiedAlbum(full);
		album.artists = full.artists.map((a) => Artist.fromSimplifiedArtist(a));
		album.copyrights = full.copyrights.map((c) => Copyright.fromCopyrightObject(c));
		album.external_ids = ExternalId.fromExternalIdObject(full.external_ids);
		album.genres = full.genres;
		album.popularity = full.popularity;
		album.release_date = full.release_date;
		album.release_date_precision = full.release_date_precision;
		album.total_tracks = full.tracks.total;
		album.tracks = tracks;
		return album;
	}

	public static fromSimplifiedAlbum(simplified: SpotifyApi.AlbumObjectSimplified): Album {
		const album = new Album();
		album.album_type = simplified.album_type;
		album.available_markets = simplified.available_markets;
		album.external_urls = ExternalUrl.fromExternalUrlObject(simplified.external_urls);
		album.href = simplified.href;
		album.id = simplified.id;
		album.images = simplified.images.map((i) => SpotifyImage.fromImageObject(i));
		album.name = simplified.name;
		album.type = simplified.type;
		album.uri = simplified.uri;
		return album;
	}
}
