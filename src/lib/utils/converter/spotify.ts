import type { 
  Album, 
  AlbumSimplified, 
  Artist, 
  ArtistSimplified, 
  Context, 
  Copyright, 
  ExternalId, 
  ExternalUrl, 
  Followers, 
  PlaybackState, 
  Playlist, 
  PlaylistSimplified, 
  PlaylistTrack, 
  SavedAlbum, 
  SavedTrack, 
  SpotifyImage, 
  Track, 
  TrackLink, 
  TrackSimplified, 
  UserDevice, 
  UserPrivate, 
  UserPublic 
} from "$lib/types/spotify";

export function toAlbumSimplified(album: SpotifyApi.AlbumObjectSimplified): AlbumSimplified {  
  return {
    album_type: album.album_type,
    available_markets: album.available_markets,
    external_urls: toExternalUrl(album.external_urls),
    href: album.href,
    id: album.id,
    images: album.images.map(i => toSpotifyImage(i)),
    name: album.name,
    type: album.type,
    uri: album.uri,
  }
}

export function toAlbum(album: SpotifyApi.AlbumObjectFull, tracks: SpotifyApi.TrackObjectFull[]): Album {
  return {
    ...toAlbumSimplified(album),
    tracks: tracks.map(t => toTrack(t)),
    artists: album.artists.map(a => toArtistSimplified(a)),
    copyrights: album.copyrights.map(c => toCopyright(c)),
    external_ids: toExternalId(album.external_ids),
    genres: album.genres,
    popularity: album.popularity,
    release_date: album.release_date,
    release_date_precision: album.release_date_precision
  }
}


export function toSavedAlbum(savedAlbum: SpotifyApi.SavedAlbumObject, tracks: SpotifyApi.TrackObjectFull[]): SavedAlbum {
  return {
    ...toAlbum(savedAlbum.album, tracks),
    added_at: savedAlbum.added_at
  }
}

export function toArtistSimplified(artist: SpotifyApi.ArtistObjectSimplified): ArtistSimplified {
  return {
    external_urls: artist.external_urls,
    href: artist.href,
    id: artist.id,
    name: artist.name,
    type: artist.type,
    uri: artist.uri
  }
}

export function toArtist(artist: SpotifyApi.ArtistObjectFull): Artist {
  return {
    ...toArtistSimplified(artist),
    followers: toFollowers(artist.followers),
    genres: artist.genres,
    images: artist.images.map(i => toSpotifyImage(i)),
    popularity: artist.popularity
  }
}

export function toCopyright(copyright: SpotifyApi.CopyrightObject): Copyright {
  return {
    text: copyright.text,
    type: copyright.type
  }
}

export function toExternalId(externalId: SpotifyApi.ExternalIdObject): ExternalId {
  return {
    isrc: externalId.isrc,
    ean: externalId.ean,
    upc: externalId.upc
  }
}

export function toExternalUrl(externalUrl: SpotifyApi.ExternalUrlObject): ExternalUrl {
  return {
    spotify: externalUrl.spotify
  }
}

export function toFollowers(followers: SpotifyApi.FollowersObject): Followers {
  return {
    href: followers.href,
    total: followers.total
  }
}

export function toSpotifyImage(spotifyImage: SpotifyApi.ImageObject): SpotifyImage {
  return {
    height: spotifyImage.height,
    url: spotifyImage.url,
    width: spotifyImage.width
  }
}

export function toPlaylistSimplified(playlist: SpotifyApi.PlaylistObjectSimplified): PlaylistSimplified {
  return {
    total_tracks: playlist.tracks.total,
    collaborative: playlist.collaborative,
    description: playlist.description,
    external_urls: toExternalUrl(playlist.external_urls),
    href: playlist.href,
    id: playlist.id,
    images: playlist.images.map(i => toSpotifyImage(i)),
    name: playlist.name,
    owner: toUserPublic(playlist.owner),
    public: playlist.public,
    snapshot_id: playlist.snapshot_id,
    type: playlist.type,
    uri: playlist.uri
  }
}

export function toPlaylist(plalylist: SpotifyApi.PlaylistObjectFull, tracks: SpotifyApi.PlaylistTrackObject[]): Playlist {
  return {
    ...toPlaylistSimplified(plalylist),
    followers: toFollowers(plalylist.followers),
    tracks: tracks.map(t => toPlaylistTrack(t))
  }
}

export function toTrackSimplified(track: SpotifyApi.TrackObjectSimplified): TrackSimplified {
  return {
    artists: track.artists.map(a => toArtistSimplified(a)),
    available_markets: track.available_markets,
    disc_number: track.disc_number,
    duration_ms: track.duration_ms,
    explicit: track.explicit,
    external_urls: toExternalUrl(track.external_urls),
    href: track.href,
    id: track.id,
    is_playable: track.is_playable,
    linked_from: track.linked_from && toTrackLink(track.linked_from),
    name: track.name,
    preview_url: track.preview_url,
    track_number: track.track_number,
    type: track.type,
    uri: track.uri
  }
}

export function toTrack(track: SpotifyApi.TrackObjectFull): Track {
  return {
    ...toTrackSimplified(track),
    album: toAlbumSimplified(track.album),
    external_ids: toExternalId(track.external_ids),
    popularity: track.popularity
  }
}

export function toSavedTrack(savedTrack: SpotifyApi.SavedTrackObject): SavedTrack {
  return {
    ...toTrack(savedTrack.track),
    added_at: savedTrack.added_at,
  }
}

export function toPlaylistTrack(playlistTrack: SpotifyApi.PlaylistTrackObject): PlaylistTrack {
  return {
    ...toTrack(playlistTrack.track as SpotifyApi.TrackObjectFull),
    added_at: playlistTrack.added_at,
    added_by: toUserPublic(playlistTrack.added_by),
    is_local: playlistTrack.is_local
  }
}
export function toTrackLink(trackLink: SpotifyApi.TrackLinkObject): TrackLink {
  return {
    external_urls: toExternalUrl(trackLink.external_urls),
    href: trackLink.href,
    id: trackLink.id,
    type: trackLink.type,
    uri: trackLink.uri
  }
}
export function toUserPublic(user: SpotifyApi.UserObjectPublic): UserPublic {
  return {
    display_name: user.display_name,
    external_urls: toExternalUrl(user.external_urls),
    followers: user.followers && toFollowers(user.followers),
    href: user.href,
    id: user.id,
    images: user.images?.map(i => toSpotifyImage(i)),
    type: user.type,
    uri: user.uri
  }
}
export function toUserPrivate(user: SpotifyApi.UserObjectPrivate): UserPrivate {
  return {
    ...toUserPublic(user),
    birthdate: user.birthdate,
    country: user.country,
    email: user.email,
    product: user.product
  }
}

export function toPlaybackState(state: SpotifyApi.CurrentPlaybackResponse): PlaybackState {
  return {
    timestamp: state.timestamp,
    device: toUserDevice(state.device),
    progress_ms: state.progress_ms,
    is_playing: state.is_playing,
    item: state.item ? toTrack(state.item) : null,
    context: state.context ? toContext(state.context) : null,
    shuffle_state: state.shuffle_state,
    repeat_state: state.repeat_state
  }
}

export function toUserDevice(device: SpotifyApi.UserDevice): UserDevice {
  return {
    id: device.id,
    is_active: device.is_active,
    is_restricted: device.is_restricted,
    name: device.name,
    type: device.type,
    volume_percent: device.volume_percent
  }
}

export function toContext(context: SpotifyApi.ContextObject): Context {
  return {
    type: context.type,
    href: context.href,
    external_urls: context.external_urls ? toExternalUrl(context.external_urls) : null,
    uri: context.uri,
  }
}