import type ClientTemplate from "../ClientTemplate";
import type {Album} from "../../../shared/classes/Album";
import type {Artist} from "../../../shared/classes/Artist";
import type {ExportifyUser} from "../../../shared/classes/ExportifyUser";
import type {Playlist} from "../../../shared/classes/Playlist";
import type {Track} from "../../../shared/classes/Track";
import type {SpotifyUser} from "../../../shared/classes/SpotifyUser";
import {Library, LibraryItem} from "../../../shared/classes/Library";
import {
  albumCollection,
  artistCollection,
  libraryCollection,
  playlistCollection,
  songCollection,
  exportifyUserCollection,
  spotifyUserCollection
} from "./collections.js"


class ClientMongoDB implements ClientTemplate {

  private static _createUpsertOption(item: { id: string | null }) {
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

  async getAlbum(id: string): Promise<Album> {
    return await albumCollection.findOne({id: id})
  }

  async getLibraryAlbums(user: ExportifyUser, libraryId: string): Promise<Album[]> {
    const library = await this.getLibrary(user, libraryId)
    const albumIds = library?.saved_albums?.item
    return await albumCollection.find({id: {$in: albumIds || []}}).toArray()
  }

  async saveAlbum(album: Album): Promise<boolean> {
    const result = await albumCollection.updateOne({id: album.id}, {$set: album}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveAlbums(albums: Album[]): Promise<boolean> {
    let bulkUpsertOptions = albums.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await albumCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.acknowledged)
  }

  async updateCurrentLibraryAlbums(owner: ExportifyUser, albums: Album[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const item = new LibraryItem(state, date, albums)
    const result = await libraryCollection.updateOne({id: currentLibrary.id}, {$set: {saved_albums: item}})
    return Promise.resolve(result.acknowledged);
  }


  // ###########################
  // ######### ARTISTS #########

  async getArtist(id: string): Promise<Artist> {
    return await artistCollection.findOne({id: id})
  }

  async getLibraryArtists(user: ExportifyUser, libraryId: string): Promise<Artist[]> {
    const library = await this.getLibrary(user, libraryId)
    const albumIds = library?.followed_artists?.item
    return await artistCollection.find({id: {$in: albumIds || []}}).toArray()
  }

  async saveArtist(artist: Artist): Promise<boolean> {
    const result = await artistCollection.updateOne({id: artist.id}, {$set: artist}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveArtists(artists: Artist[]): Promise<boolean> {
    let bulkUpsertOptions = artists.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await artistCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.acknowledged)
  }

  async updateCurrentLibraryArtists(owner: ExportifyUser, artists: Artist[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const item = new LibraryItem(state, date, artists)
    const result = await libraryCollection.updateOne({id: currentLibrary.id}, {$set: {followed_artists: item}})
    return Promise.resolve(result.acknowledged);
  }


  // #############################
  // ######### PLAYLISTS #########

  async getPlaylist(id: string): Promise<Playlist> {
    return await playlistCollection.findOne({id: id})
  }

  async getLibraryPlaylists(user: ExportifyUser, libraryId: string): Promise<Playlist[]> {
    const library = await this.getLibrary(user, libraryId)
    const albumIds = library?.playlists?.item
    return await playlistCollection.find({id: {$in: albumIds || []}}).toArray()
  }

  async savePlaylist(playlist: Playlist): Promise<boolean> {
    const result = await playlistCollection.updateOne({id: playlist.id}, {$set: playlist}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async savePlaylists(playlists: Playlist[]): Promise<boolean> {
    let bulkUpsertOptions = playlists.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await playlistCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.acknowledged)
  }

  async updateCurrentLibraryPlaylists(owner: ExportifyUser, playlists: Playlist[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const item = new LibraryItem(state, date, playlists)
    const result = await libraryCollection.updateOne({id: currentLibrary.id}, {$set: {playlists: item}})
    return Promise.resolve(result.acknowledged);
  }


  // ##########################
  // ######### TRACKS #########

  async getTrack(id: string): Promise<Track> {
    return await songCollection.findOne({id: id})
  }

  async getLibraryTracks(user: ExportifyUser, libraryId: string): Promise<Track[]> {
    const library = await this.getLibrary(user, libraryId)
    const albumIds = library?.saved_tracks?.item
    return await songCollection.find({id: {$in: albumIds || []}}).toArray()
  }

  async saveTrack(track: Track): Promise<boolean> {
    const result = await songCollection.updateOne({id: track.id}, {$set: track}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveTracks(tracks: Track[]): Promise<boolean> {
    let bulkUpsertOptions = tracks.map((item) => ClientMongoDB._createUpsertOption(item))
    const result = await songCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.acknowledged)
  }

  async updateCurrentLibraryTracks(owner: ExportifyUser, tracks: Track[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const item = new LibraryItem(state, date, tracks)
    const result = await libraryCollection.updateOne({id: currentLibrary.id}, {$set: {playlists: item}})
    return Promise.resolve(result.acknowledged);
  }


  // #############################
  // ######### Libraries #########

  async initCurrentLibrary(owner: ExportifyUser): Promise<boolean> {
    const currentLibrary = await libraryCollection.findOne({"owner.item": owner.id, type: Library.Type.current})

    if (currentLibrary !== null) {
      return Promise.resolve(false);
    }

    const library = new Library()
    library.type = Library.Type.current
    library.owner = new LibraryItem(Library.Status.ready, new Date().toString(), owner.id)
    const result = await libraryCollection.insertOne(library)
    return Promise.resolve(result.acknowledged);
  }

  async saveSnapshotLibrary(library: Library): Promise<boolean> {
    const result = await libraryCollection.insertOne(library)
    return Promise.resolve(result.acknowledged);
  }

  async getAllUserLibraries(owner: ExportifyUser): Promise<Library[]> {
    return await libraryCollection.find({"owner.item": owner.id}).toArray()
  }

  async getCurrentLibrary(owner: ExportifyUser): Promise<Library> {
    return await libraryCollection.findOne({"owner.item": owner.id, type: Library.Type.current})
  }

  async getLibrary(owner: ExportifyUser, libraryId: string): Promise<Library> {
    if (libraryId === Library.Type.current) {
      return await this.getCurrentLibrary(owner)
    }

    return await libraryCollection.findOne({id: libraryId})
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

  async getExportifyUser(id: string): Promise<ExportifyUser> {
    return await exportifyUserCollection.findOne({id: id})
  }

  async getSpotifyUser(id: string): Promise<SpotifyUser> {
    return await spotifyUserCollection.findOne({id: id})
  }

}

export default ClientMongoDB