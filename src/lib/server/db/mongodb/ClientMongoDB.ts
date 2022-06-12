import type ClientTemplate from "../ClientTemplate";
import type {Album} from "../../../types/Album";
import type {Artist} from "../../../types/Artist";
import type {ExportifyUser} from "../../../types/ExportifyUser";
import type {Playlist} from "../../../types/Playlist";
import type {LibraryTrack, Track} from "../../../types/Track";
import type {SpotifyUser} from "../../../types/SpotifyUser";
import {
  albumCollection,
  artistCollection,
  exportifyUserCollection,
  libraryCollection,
  playlistCollection,
  trackCollection,
  spotifyUserCollection
} from "./collections.js"
import type {WithId} from "mongodb";
import {
  Library,
  LibraryItem,
  LibraryStatus,
  LibraryType
} from "../../../types/Library";
import {ObjectId} from "mongodb";
import type {LibraryAlbum} from "../../../types/Album";
import type {LibraryArtist} from "../../../types/Artist";
import type {LibraryPlaylist} from "../../../types/Playlist";



class ClientMongoDB implements ClientTemplate {

  private static _createUpsertOption(item: { id: string }) {
    return {
      "updateOne": {
        "filter": {id: item.id},
        "update": {$set: item},
        ...{upsert: true}
      }
    }
  }

  // ##########################
  // ######### ALBUMS #########

  async getAlbum(id: string): Promise<WithId<Album> | null>  {
    return await albumCollection.findOne({id: id})
  }

  async getLibraryAlbums(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryAlbum[]>> {
    const library = await this.getLibrary(user, libraryId)
    return Promise.resolve(library.saved_albums)
  }

  async saveAlbum(album: Album): Promise<boolean> {
    const result = await albumCollection.updateOne({id: album.id}, {$set: album}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveAlbums(albums: Album[]): Promise<boolean> {
    let bulkUpsertOptions = albums.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await albumCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.isOk())
  }

  async updateCurrentLibraryAlbums(owner: ExportifyUser, albums: LibraryAlbum[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    let date = new Date().toString()
    const item = new LibraryItem(state, date, albums)
    const result = await libraryCollection.updateOne({_id: currentLibrary._id}, {$set: {saved_albums: item}})
    return Promise.resolve(result.acknowledged);
  }


  // ###########################
  // ######### ARTISTS #########

  async getArtist(id: string): Promise<WithId<Artist> | null> {
    return await artistCollection.findOne({id: id})
  }

  async getLibraryArtists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryArtist[]>> {
    const library = await this.getLibrary(user, libraryId)
    return Promise.resolve(library.followed_artists)
  }

  async saveArtist(artist: Artist): Promise<boolean> {
    const result = await artistCollection.updateOne({id: artist.id}, {$set: artist}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveArtists(artists: Artist[]): Promise<boolean> {
    let bulkUpsertOptions = artists.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await artistCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.isOk())
  }

  async updateCurrentLibraryArtists(owner: ExportifyUser, artists: LibraryArtist[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    let date = new Date().toString()
    const item = new LibraryItem(state, date, artists)
    const result = await libraryCollection.updateOne({_id: currentLibrary._id}, {$set: {followed_artists: item}})
    return Promise.resolve(result.acknowledged);
  }


  // #############################
  // ######### PLAYLISTS #########

  async getPlaylist(id: string): Promise<WithId<Playlist> | null> {
    return await playlistCollection.findOne({id: id})
  }

  async getLibraryPlaylists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryPlaylist[]>> {
    const library = await this.getLibrary(user, libraryId)
    return Promise.resolve(library.playlists)
  }

  async savePlaylist(playlist: Playlist): Promise<boolean> {
    const result = await playlistCollection.updateOne({id: playlist.id}, {$set: playlist}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async savePlaylists(playlists: Playlist[]): Promise<boolean> {
    let bulkUpsertOptions = playlists.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await playlistCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.isOk())
  }

  async updateCurrentLibraryPlaylists(owner: ExportifyUser, playlists: LibraryPlaylist[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    let date = new Date().toString()
    const item = new LibraryItem(state, date, playlists)
    const result = await libraryCollection.updateOne({_id: currentLibrary._id}, {$set: {playlists: item}})
    return Promise.resolve(result.acknowledged);
  }


  // ##########################
  // ######### TRACKS #########

  async getTrack(id: string): Promise<WithId<Track> | null> {
    return await trackCollection.findOne({id: id})
  }

  async getLibraryTracks(user: ExportifyUser, libraryId: string): Promise<LibraryItem<LibraryTrack[]>> {
    const library = await this.getLibrary(user, libraryId)
    return Promise.resolve(library.saved_tracks)
  }

  async saveTrack(track: Track): Promise<boolean> {
    const result = await trackCollection.updateOne({id: track.id}, {$set: track}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveTracks(tracks: Track[]): Promise<boolean> {
    let bulkUpsertOptions = tracks.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await trackCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.isOk())
  }

  async updateCurrentLibraryTracks(owner: ExportifyUser, tracks: LibraryTrack[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    let date = new Date().toString()
    const item = new LibraryItem(state, date, tracks)
    const result = await libraryCollection.updateOne({_id: currentLibrary._id}, {$set: {saved_tracks: item}})
    return Promise.resolve(result.acknowledged);
  }


  // #############################
  // ######### Libraries #########

  async initCurrentLibrary(owner: ExportifyUser): Promise<boolean> {
    const currentLibrary = await libraryCollection.findOne({"owner.item": owner.id, type: LibraryType.current})

    if (currentLibrary !== null) {
      return Promise.resolve(false);
    }

    const libOwner = new LibraryItem(LibraryStatus.ready, new Date().toString(), owner.id)
    const type = LibraryType.current
    const library = new Library(libOwner, type)
    const result = await libraryCollection.insertOne(library)
    return Promise.resolve(result.acknowledged);
  }

  async saveSnapshotLibrary(library: Library): Promise<boolean> {
    const result = await libraryCollection.insertOne(library)
    return Promise.resolve(result.acknowledged);
  }

  async getAllUserLibraries(owner: ExportifyUser): Promise<WithId<Library>[]> {
    return await libraryCollection.find({"owner.item": owner.id}).toArray()
  }

  async getCurrentLibrary(owner: ExportifyUser): Promise<WithId<Library>> {
    const library = await libraryCollection.findOne({"owner.item": owner.id, type: LibraryType.current})
    if (library === null) throw new Error("Library Id not found")
    return library
  }

  async getLibrary(owner: ExportifyUser, libraryId: string): Promise<WithId<Library>> {
    if (libraryId === LibraryType.current) {
      return await this.getCurrentLibrary(owner)
    }
    const library = await libraryCollection.findOne({_id: new ObjectId(libraryId)})
    if (library === null) throw new Error("Library Id not found")
    return library
  }


  // ########################
  // ######### USER #########

  async saveExportifyUser(exportifyUser: ExportifyUser): Promise<boolean> {
    const result = await exportifyUserCollection.updateOne({id: exportifyUser.id}, {$set: exportifyUser}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveSpotifyUser(spotifyUser: SpotifyUser): Promise<boolean> {
    const result = await spotifyUserCollection.updateOne({id: spotifyUser.id}, {$set: spotifyUser}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async getExportifyUser(id: string): Promise<WithId<ExportifyUser> | null> {
    return await exportifyUserCollection.findOne({id: id})
  }

  async getSpotifyUser(id: string): Promise<WithId<SpotifyUser> | null> {
    return await spotifyUserCollection.findOne({id: id})
  }

}

export default ClientMongoDB