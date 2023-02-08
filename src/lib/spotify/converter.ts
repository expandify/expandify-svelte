export function albumSimplified(album: SpotifyApi.AlbumObjectSimplified): AlbumSimplified {  
  return {
    album_type: album.album_type,
    available_markets: album.available_markets,
    external_urls: externalUrl(album.external_urls),
    href: album.href,
    id: album.id,
    images: album.images.map(i => spotifyImage(i)),
    name: album.name,
    type: album.type,
    uri: album.uri,
  }
}

export function album(album: SpotifyApi.AlbumObjectFull, tracks: Track[]): Album {
  return {
    ...albumSimplified(album),
    tracks: tracks,
    artists: album.artists.map(a => artistSimplified(a)),
    copyrights: album.copyrights.map(c => copyright(c)),
    external_ids: externalId(album.external_ids),
    genres: album.genres,
    popularity: album.popularity,
    release_date: album.release_date,
    release_date_precision: album.release_date_precision
  }
}


export function savedAlbum(savedAlbum: SpotifyApi.SavedAlbumObject, tracks: Track[]): SavedAlbum {
  return {
    ...album(savedAlbum.album, tracks),
    added_at: savedAlbum.added_at
  }
}

export function artistSimplified(artist: SpotifyApi.ArtistObjectSimplified): ArtistSimplified {
  return {
    external_urls: artist.external_urls,
    href: artist.href,
    id: artist.id,
    name: artist.name,
    type: artist.type,
    uri: artist.uri
  }
}

export function artist(artist: SpotifyApi.ArtistObjectFull): Artist {
  return {
    ...artistSimplified(artist),
    followers: followers(artist.followers),
    genres: artist.genres,
    images: artist.images.map(i => spotifyImage(i)),
    popularity: artist.popularity
  }
}

export function copyright(copyright: SpotifyApi.CopyrightObject): Copyright {
  return {
    text: copyright.text,
    type: copyright.type
  }
}

export function externalId(externalId: SpotifyApi.ExternalIdObject): ExternalId {
  return {
    isrc: externalId.isrc,
    ean: externalId.ean,
    upc: externalId.upc
  }
}

export function externalUrl(externalUrl: SpotifyApi.ExternalUrlObject): ExternalUrl {
  return {
    spotify: externalUrl.spotify
  }
}

export function followers(followers: SpotifyApi.FollowersObject): Followers {
  return {
    href: followers.href,
    total: followers.total
  }
}

export function spotifyImage(spotifyImage: SpotifyApi.ImageObject): SpotifyImage {
  return {
    height: spotifyImage.height,
    url: spotifyImage.url,
    width: spotifyImage.width
  }
}

export function playlistSimplified(playlist: SpotifyApi.PlaylistObjectSimplified): PlaylistSimplified {
  return {
    total_tracks: playlist.tracks.total,
    collaborative: playlist.collaborative,
    description: playlist.description,
    external_urls: externalUrl(playlist.external_urls),
    href: playlist.href,
    id: playlist.id,
    images: playlist.images.map(i => spotifyImage(i)),
    name: playlist.name,
    owner: userPublic(playlist.owner),
    public: playlist.public,
    snapshot_id: playlist.snapshot_id,
    type: playlist.type,
    uri: playlist.uri
  }
}

export function playlist(plalylist: SpotifyApi.PlaylistObjectFull, tracks: PlaylistTrack[]): Playlist {
  return {
    ...playlistSimplified(plalylist),
    followers: followers(plalylist.followers),
    tracks: tracks
  }
}

export function trackSimplified(track: SpotifyApi.TrackObjectSimplified): TrackSimplified {
  return {
    artists: track.artists.map(a => artistSimplified(a)),
    available_markets: track.available_markets,
    disc_number: track.disc_number,
    duration_ms: track.duration_ms,
    explicit: track.explicit,
    external_urls: externalUrl(track.external_urls),
    href: track.href,
    id: track.id,
    is_playable: track.is_playable,
    linked_from: track.linked_from && trackLink(track.linked_from),
    name: track.name,
    preview_url: track.preview_url,
    track_number: track.track_number,
    type: track.type,
    uri: track.uri
  }
}

export function track(track: SpotifyApi.TrackObjectFull): Track {
  return {
    ...trackSimplified(track),
    album: albumSimplified(track.album),
    external_ids: externalId(track.external_ids),
    popularity: track.popularity
  }
}

export function savedTrack(savedTrack: SpotifyApi.SavedTrackObject): SavedTrack {
  return {
    ...track(savedTrack.track),
    added_at: savedTrack.added_at,
  }
}

export function playlistTrack(playlistTrack: SpotifyApi.PlaylistTrackObject): PlaylistTrack {
  return {
    ...track(playlistTrack.track as SpotifyApi.TrackObjectFull),
    added_at: playlistTrack.added_at,
    added_by: userPublic(playlistTrack.added_by),
    is_local: playlistTrack.is_local
  }
}
export function trackLink(trackLink: SpotifyApi.TrackLinkObject): TrackLink {
  return {
    external_urls: externalUrl(trackLink.external_urls),
    href: trackLink.href,
    id: trackLink.id,
    type: trackLink.type,
    uri: trackLink.uri
  }
}
export function userPublic(user: SpotifyApi.UserObjectPublic): UserPublic {
  return {
    display_name: user.display_name,
    external_urls: externalUrl(user.external_urls),
    followers: user.followers && followers(user.followers),
    href: user.href,
    id: user.id,
    images: user.images?.map(i => spotifyImage(i)),
    type: user.type,
    uri: user.uri
  }
}
export function userPrivate(user: SpotifyApi.UserObjectPrivate): UserPrivate {
  return {
    ...userPublic(user),
    birthdate: user.birthdate,
    country: user.country,
    email: user.email,
    product: user.product
  }
}


export function paging<T, F>(page: SpotifyApi.PagingObject<F>, items: T[]): Paging<T> {
  return {
    href: page.href,
    items: items,
    limit: page.limit,
    next: page.next,
    offset: page.offset,
    previous: page.previous,
    total: page.total
  }
}

export function cursor<T, F>(cursor: SpotifyApi.CursorBasedPagingObject<F>, items: T[]): Cursor<T> {
  return {
    href: cursor.href,
    items: items,
    limit: cursor.limit,
    next: cursor.next,
    cursors: {
      after: cursor.cursors.after,
      before: cursor.cursors.before
    },
    total: cursor.total
  }
}