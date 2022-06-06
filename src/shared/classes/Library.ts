import {Album} from "./Album";
import {Artist} from "./Artist";
import {Track} from "./Track";
import {Playlist} from "./Playlist";

export class Library {
  saved_albums: Album[]
  followed_artists: Artist[]
  saved_tracks: Track[]
  playlists: Playlist[]

}
