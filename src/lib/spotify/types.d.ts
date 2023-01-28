declare interface SpotifyData {}

declare interface AlbumSimplified extends SpotifyData {	
	album_type: string;
	available_markets?: string[];
	external_urls: ExternalUrl;
	href: string;
	id: string;
	images: SpotifyImage[];
	name: string;
	type: 'album';
	uri: string;
}

declare interface Album extends AlbumSimplified {
	tracks: TrackSimplified[]
	artists: ArtistSimplified[];
	copyrights: Copyright[];
	external_ids: ExternalId;
	genres: string[];
	popularity: number;
	release_date: string;
	release_date_precision: string;
}

declare interface SavedAlbum extends Album {
	added_at: string;
}

declare interface ArtistSimplified extends SpotifyData {
	external_urls: ExternalUrl;
	href: string;
	id: string;
	name: string;
	type: 'artist';
	uri: string;
}

declare interface Artist extends ArtistSimplified {
	followers: Followers;
	genres: string[];
	images: SpotifyImage[];
	popularity: number;
}

declare interface Copyright extends SpotifyData {
	text: string;
	type: 'C' | 'P';
}

declare interface ExternalId extends SpotifyData {
	isrc?: string;
	ean?: string;
	upc?: string;
}

declare interface ExternalUrl extends SpotifyData {
	spotify: string;
}

declare interface Followers extends SpotifyData {
	href: string;
	total: number;
}

declare interface SpotifyImage extends SpotifyData {
	height?: number;
	url: string;
	width?: number;
}

declare interface PlaylistSimplified extends SpotifyData {
	total_tracks: number;
	collaborative: boolean;
	description: string | null;
	external_urls: ExternalUrl;
	href: string;
	id: string;
	images: SpotifyImage[];
	name: string;
	owner: UserPublic;
	public: boolean;
	snapshot_id: string;
	type: 'playlist';
	uri: string;
}

declare interface Playlist extends PlaylistSimplified {
	followers: Followers;
	tracks: PlaylistTrack[]
}

declare interface TrackSimplified extends SpotifyData {
	artists: ArtistSimplified[];
	available_markets?: string[];
	disc_number: number;
	duration_ms: number;
	explicit: boolean;
	external_urls: ExternalUrl;
	href: string;
	id: string;
	is_playable?: boolean;
	linked_from?: TrackLink;
	name: string;
	preview_url: string;
	track_number: number;
	type: 'track' | 'episode';
	uri: string;
}

declare interface Track extends TrackSimplified {
	album: AlbumSimplified;
	external_ids: ExternalId;
	popularity: number;	
}

declare interface SavedTrack extends Track {
	added_at: string;
}


declare interface PlaylistTrack extends SavedTrack {
	is_local: boolean;
	added_by: UserPublic;
}


declare interface TrackLink {
	external_urls: ExternalUrl;
	href: string;
	id: string;
	type: 'track';
	uri: string;
}

declare interface UserPublic extends SpotifyData {
	display_name?: string;
	external_urls: ExternalUrl;
	followers?: Followers;
	href: string;
	id: string;
	images?: SpotifyImage[];
	type: 'user';
	uri: string;
}

declare interface UserPrivate extends UserPublic {
	birthdate: string;
	country: string;
	email: string;
	product: string;	
}

declare interface Paging<T> {
	href: string;
	items: T[];
	limit: number;
	next: string;
	offset: number;
	previous: string;
	total: number;
}

declare interface Cursor<T> {
	href: string;
	items: T[];
	limit: number;
	next: string;
	cursors: {
    after: string;
    before?: string;
  };
	total?: number;
}