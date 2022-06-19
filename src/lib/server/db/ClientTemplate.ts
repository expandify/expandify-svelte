import type {Album, LibraryAlbum} from "$lib/types/Album";
import type {Artist} from "$lib/types/Artist";
import type {ExportifyUser} from "$lib/types/ExportifyUser";
import type {SpotifyUser} from "$lib/types/SpotifyUser";
import type {Track} from "$lib/types/Track";
import type {LibraryPlaylist, Playlist} from "$lib/types/Playlist";
import type {Library} from "$lib/types/Library";
import type {LibraryItem} from "$lib/types/Library";
import type {WithId} from "mongodb";
import type {LibraryTrack} from "$lib/types/Track";
import type {LibraryArtist} from "$lib/types/Artist";


interface ClientTemplate {

  saveAlbum(album: Album): Promise<boolean>
  saveAlbums(albums: Album[]): Promise<boolean>
  updateCurrentLibraryAlbums(owner: ExportifyUser, albums: LibraryAlbum[], state: number): Promise<boolean>
  getAlbum(id: string): Promise<WithId<Album> | null>
  getLibraryAlbums(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryAlbum[]>>

  saveArtist(artist: Artist): Promise<boolean>
  saveArtists(artists: Artist[]): Promise<boolean>
  updateCurrentLibraryArtists(owner: ExportifyUser, artists: LibraryArtist[], state: number): Promise<boolean>
  getArtist(id: string): Promise<WithId<Artist> | null>
  getLibraryArtists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryArtist[]>>

  savePlaylist(playlist: Playlist): Promise<boolean>
  savePlaylists(playlists: Playlist[]): Promise<boolean>
  updateCurrentLibraryPlaylists(owner: ExportifyUser, playlists: LibraryPlaylist[], state: number): Promise<boolean>
  getPlaylist(id: string): Promise<WithId<Playlist> | null>
  getLibraryPlaylists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryPlaylist[]>>

  saveTrack(track: Track): Promise<boolean>
  saveTracks(tracks: Track[]): Promise<boolean>
  updateCurrentLibraryTracks(owner: ExportifyUser, tracks: LibraryTrack[], state: number): Promise<boolean>
  getTrack(id: string): Promise<WithId<Track> | null>
  getLibraryTracks(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryTrack[]>>

  initCurrentLibrary(owner: ExportifyUser): Promise<boolean>
  saveSnapshotLibrary(library: Library): Promise<boolean>
  getAllUserLibraries(owner: ExportifyUser): Promise<WithId<Library>[]>
  getCurrentLibrary(owner: ExportifyUser): Promise<WithId<Library>>
  getLibrary(owner: ExportifyUser, libraryId: string): Promise<WithId<Library>>

  saveSpotifyUser(spotifyUser: SpotifyUser): Promise<boolean>
  saveExportifyUser(exportifyUser: ExportifyUser): Promise<boolean>
  getSpotifyUser(id: string): Promise<WithId<SpotifyUser> | null>
  getExportifyUser(id: string): Promise<WithId<ExportifyUser> | null>

}

export default ClientTemplate