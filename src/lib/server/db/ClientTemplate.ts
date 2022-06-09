import type {Album} from "../../shared/classes/Album";
import type {Artist} from "../../shared/classes/Artist";
import type {ExportifyUser} from "../../shared/classes/ExportifyUser";
import type {SpotifyUser} from "../../shared/classes/SpotifyUser";
import type {Track} from "../../shared/classes/Track";
import type {Playlist} from "../../shared/classes/Playlist";
import type {Library} from "../../shared/classes/Library";
import type {LibraryItem} from "../../shared/classes/Library";
import type {WithId} from "mongodb";


interface ClientTemplate {

  saveAlbum(album: Album): Promise<boolean>
  saveAlbums(albums: Album[]): Promise<boolean>
  updateCurrentLibraryAlbums(owner: ExportifyUser, albums: Album[], state: number): Promise<boolean>
  getAlbum(id: string): Promise<WithId<Album> | null>
  getLibraryAlbums(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Album>[]>>

  saveArtist(artist: Artist): Promise<boolean>
  saveArtists(artists: Artist[]): Promise<boolean>
  updateCurrentLibraryArtists(owner: ExportifyUser, artists: Artist[], state: number): Promise<boolean>
  getArtist(id: string): Promise<WithId<Artist> | null>
  getLibraryArtists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Artist>[]>>

  savePlaylist(playlist: Playlist): Promise<boolean>
  savePlaylists(playlists: Playlist[]): Promise<boolean>
  updateCurrentLibraryPlaylists(owner: ExportifyUser, playlists: Playlist[], state: number): Promise<boolean>
  getPlaylist(id: string): Promise<WithId<Playlist> | null>
  getLibraryPlaylists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Playlist>[]>>

  saveTrack(track: Track): Promise<boolean>
  saveTracks(tracks: Track[]): Promise<boolean>
  updateCurrentLibraryTracks(owner: ExportifyUser, tracks: Track[], state: number): Promise<boolean>
  getTrack(id: string): Promise<WithId<Track> | null>
  getLibraryTracks(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Track>[]>>

  initCurrentLibrary(owner: ExportifyUser): Promise<boolean>
  saveSnapshotLibrary(library: Library): Promise<boolean>
  getAllUserLibraries(owner: ExportifyUser): Promise<WithId<Library>[]>
  getCurrentLibrary(owner: ExportifyUser): Promise<WithId<Library> | null>
  getLibrary(owner: ExportifyUser, libraryId: string): Promise<WithId<Library> | null>

  saveSpotifyUser(spotifyUser: SpotifyUser): Promise<boolean>
  saveExportifyUser(exportifyUser: ExportifyUser): Promise<boolean>
  getSpotifyUser(id: string): Promise<WithId<SpotifyUser> | null>
  getExportifyUser(id: string): Promise<WithId<ExportifyUser> | null>

}

export default ClientTemplate