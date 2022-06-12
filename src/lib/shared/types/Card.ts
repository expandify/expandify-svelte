import {imageSelector, joinOn} from "../helpers";
import type {LibraryAlbum} from "./Album";
import type {LibraryArtist} from "./Artist";
import type {LibraryPlaylist} from "./Playlist";

export interface Card {
  image: string | null
  title: string
  subtitle?: string | null
  href?: string | null
  id?: string | null
}

export function albumToCard(album: LibraryAlbum): Card {
  return {
    title: album.album.name,
    subtitle: joinOn(album.album.artists, "name"),
    image:  imageSelector(album.album.images),
    id: album.album.id
  }
}

export function artistToCard(artist: LibraryArtist): Card {
  return {
    title: artist.artist.name,
    image: imageSelector(artist.artist.images),
    id: artist.artist.id
  }
}

export function playlistToCard(playlist: LibraryPlaylist): Card {
  return {
    title: playlist.playlist.name,
    subtitle: playlist.playlist.owner.name,
    image:imageSelector(playlist.playlist.images),
    id: playlist.playlist.id
  }
}