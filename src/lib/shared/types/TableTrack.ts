import type {LibraryTrack} from "./Track";
import {formatDate, imageSelector, joinOn, msToTime} from "../helpers";

export interface TableTrack {
  name: string
  artists: { id: string, name: string }[]
  artists_joined: string,
  album: { id: string, name: string }
  added_at: string
  duration: string
  image: string
  id: string

  // These are for search optimizations, so that .toLowerCase() does not need to be called every time
  album_lower_case: string
  name_lower_case: string
  artists_joined_lower_case: string
}

export function toTableTrack(track: LibraryTrack) {
  return {
    name: track.track.name,
    artists: track.track.artists.map(artist => ({id: artist.id, name: artist.name})),
    artists_joined: joinOn(track.track.artists, "name"),
    album: {id: track.track.album.id, name: track.track.album.name},
    added_at: track.added_at ? formatDate(track.added_at) : "",
    duration: msToTime(track.track.duration_ms),
    image: imageSelector(track.track.album?.images),
    id: track.track.id,
    name_lower_case: track.track.name.toLowerCase(),
    artists_joined_lower_case: joinOn(track.track.artists, "name").toLowerCase(),
    album_lower_case: track.track.album.name.toLowerCase()
  }
}