export interface SpotifyData {
}

export interface AlbumSimplified extends SpotifyData {
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

export interface Album extends AlbumSimplified {
    tracks: Track[]
    artists: ArtistSimplified[];
    copyrights: Copyright[];
    external_ids: ExternalId;
    genres: string[];
    popularity: number;
    release_date: string;
    release_date_precision: string;
}

export interface SavedAlbum extends Album {
    added_at: string;
}

export interface ArtistSimplified extends SpotifyData {
    external_urls: ExternalUrl;
    href: string;
    id: string;
    name: string;
    type: 'artist';
    uri: string;
}

export interface Artist extends ArtistSimplified {
    followers: Followers;
    genres: string[];
    images: SpotifyImage[];
    popularity: number;
}

export interface Copyright extends SpotifyData {
    text: string;
    type: 'C' | 'P';
}

export interface ExternalId extends SpotifyData {
    isrc?: string;
    ean?: string;
    upc?: string;
}

export interface ExternalUrl extends SpotifyData {
    spotify: string;
}

export interface Followers extends SpotifyData {
    href: string;
    total: number;
}

export interface SpotifyImage extends SpotifyData {
    height?: number;
    url: string;
    width?: number;
}

export interface PlaylistSimplified extends SpotifyData {
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

export interface Playlist extends PlaylistSimplified {
    followers: Followers;
    tracks: PlaylistTrack[]
}

export interface TrackSimplified extends SpotifyData {
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

export interface Track extends TrackSimplified {
    album: AlbumSimplified;
    external_ids: ExternalId;
    popularity: number;
}

export interface SavedTrack extends Track {
    added_at: string;
}


export interface PlaylistTrack extends SavedTrack {
    is_local: boolean;
    added_by: UserPublic;
}


export interface TrackLink {
    external_urls: ExternalUrl;
    href: string;
    id: string;
    type: 'track';
    uri: string;
}

export interface UserPublic extends SpotifyData {
    display_name?: string;
    external_urls: ExternalUrl;
    followers?: Followers;
    href: string;
    id: string;
    images?: SpotifyImage[];
    type: 'user';
    uri: string;
}

export interface UserPrivate extends UserPublic {
    birthdate: string;
    country: string;
    email: string;
    product: string;
}

export interface PlaybackState extends SpotifyData {
    timestamp: number;
    device: UserDevice;
    progress_ms: number | null;
    is_playing: boolean;
    item: Track | null;
    context: Context | null;
    shuffle_state: boolean;
    repeat_state: 'off' | 'track' | 'context';
}

export interface UserDevice extends SpotifyData {
    id: string | null;
    is_active: boolean;
    is_restricted: boolean;
    name: string;
    type: string;
    volume_percent: number | null;
}

export interface Context extends SpotifyData {
    type: 'artist' | 'playlist' | 'album';
    href: string | null;
    external_urls: ExternalUrl | null;
    uri: string;
}