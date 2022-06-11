import type ClientTemplate from "../ClientTemplate";
import type {Album} from "../../../shared/types/Album";
import type {Artist} from "../../../shared/types/Artist";
import type {ExportifyUser} from "../../../shared/types/ExportifyUser";
import type {Playlist} from "../../../shared/types/Playlist";
import type {Track} from "../../../shared/types/Track";
import type {SpotifyUser} from "../../../shared/types/SpotifyUser";
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
  type LibraryTrack,
  LibraryType
} from "../../../shared/types/Library";
import {ObjectId} from "mongodb";


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

  async getLibraryAlbums(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Album>[]>> {
    const library = await this.getLibrary(user, libraryId)
    const status = library?.saved_albums?.status || LibraryStatus.error
    const last_updated = library?.saved_albums?.last_updated || null

    if (status !== LibraryStatus.ready) {
      return Promise.resolve(new LibraryItem(status, last_updated, []))
    }
    const albumIds = library?.saved_albums?.item || []
    const albums: WithId<Album>[] = await albumCollection.find({id: {$in: albumIds}}).toArray()
    return Promise.resolve(new LibraryItem(status, last_updated, albums))
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

  async updateCurrentLibraryAlbums(owner: ExportifyUser, albums: Album[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const item = new LibraryItem(state, date, albums.map(value => value.id).filter(x => !!x)) as LibraryItem<string[]>
    const result = await libraryCollection.updateOne({_id: currentLibrary._id}, {$set: {saved_albums: item}})
    return Promise.resolve(result.acknowledged);
  }


  // ###########################
  // ######### ARTISTS #########

  async getArtist(id: string): Promise<WithId<Artist> | null> {
    return await artistCollection.findOne({id: id})
  }

  async getLibraryArtists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Artist>[]>> {
    const library = await this.getLibrary(user, libraryId)
    const status = library?.followed_artists?.status || LibraryStatus.error
    const last_updated = library?.followed_artists?.last_updated || null
    if (status !== LibraryStatus.ready) {
      return Promise.resolve(new LibraryItem(status, last_updated, []))
    }
    const artistsIds = library?.followed_artists?.item || []
    const artists: WithId<Artist>[] = await artistCollection.find({id: {$in: artistsIds}}).toArray()
    return Promise.resolve(new LibraryItem(status, last_updated, artists))
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

  async updateCurrentLibraryArtists(owner: ExportifyUser, artists: Artist[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const item = new LibraryItem(state, date, artists.map(value => value.id).filter(x => !!x)) as LibraryItem<string[]>
    const result = await libraryCollection.updateOne({_id: currentLibrary._id}, {$set: {followed_artists: item}})
    return Promise.resolve(result.acknowledged);
  }


  // #############################
  // ######### PLAYLISTS #########

  async getPlaylist(id: string): Promise<WithId<Playlist> | null> {
    return await playlistCollection.findOne({id: id})
  }

  async getLibraryPlaylists(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Playlist>[]>> {
    const library = await this.getLibrary(user, libraryId)
    const status = library?.playlists?.status || LibraryStatus.error
    const last_updated = library?.playlists?.last_updated || null
    if (status !== LibraryStatus.ready) {
      return Promise.resolve(new LibraryItem(status, last_updated, []))
    }
    const playlistIds: string[] = library?.playlists?.item || []
    const playlists: WithId<Playlist>[] = await playlistCollection.find({id: {$in: playlistIds}}).toArray()
    return Promise.resolve(new LibraryItem(status, last_updated, playlists))
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

  async updateCurrentLibraryPlaylists(owner: ExportifyUser, playlists: Playlist[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const item = new LibraryItem(state, date, playlists.map(value => value.id).filter(x => !!x)) as LibraryItem<string[]>
    const result = await libraryCollection.updateOne({_id: currentLibrary._id}, {$set: {playlists: item}})
    return Promise.resolve(result.acknowledged);
  }


  // ##########################
  // ######### TRACKS #########

  async getTrack(id: string): Promise<WithId<Track> | null> {
    return await trackCollection.findOne({id: id})
  }

  async getLibraryTracks(user: ExportifyUser, libraryId: string): Promise<LibraryItem<WithId<Track>[]>> {
    const library = await this.getLibrary(user, libraryId)
    const status = library?.saved_tracks?.status || LibraryStatus.error
    const last_updated = library?.saved_tracks?.last_updated || null
    if (status !== LibraryStatus.ready) {
      return Promise.resolve(new LibraryItem(status, last_updated, []))
    }
    const libraryTracks = library?.saved_tracks?.item || []
    const trackIds = libraryTracks.map(value => value.id)

    const tracks: WithId<Track>[] = await trackCollection.find({id: {$in: trackIds}}).toArray()

    const tracksWithDate = tracks.map(t1 => ({...t1, ...libraryTracks.find(t2 => t2.id === t1.id)}))

    return Promise.resolve(new LibraryItem(status, last_updated, tracksWithDate))
  }

  async saveTrack(track: Track): Promise<boolean> {
    const result = await trackCollection.updateOne({id: track.id}, {$set: track}, {upsert: true})
    return Promise.resolve(result.acknowledged)
  }

  async saveTracks(tracks: Track[]): Promise<boolean> {
    let bulkUpsertOptions = tracks.map((item) => {
      // added_at is user specific and should not be persisted to the global track document
      delete item.added_at
      return ClientMongoDB._createUpsertOption(item)
    })
    const result = await trackCollection.bulkWrite(bulkUpsertOptions)
    return Promise.resolve(result.isOk())
  }

  async updateCurrentLibraryTracks(owner: ExportifyUser, tracks: Track[], state: number): Promise<boolean> {
    const currentLibrary = await this.getCurrentLibrary(owner)
    if (currentLibrary === null) {
      throw new Error("No Current Library found")
    }
    let date = new Date().toString()
    const libraryTracks = tracks.map(value => ({id: value.id, added_at: value.added_at}))
    const item = new LibraryItem(state, date, libraryTracks.filter(x => !!x)) as LibraryItem<LibraryTrack[]>
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

  async getCurrentLibrary(owner: ExportifyUser): Promise<WithId<Library> | null> {
    return await libraryCollection.findOne({"owner.item": owner.id, type: LibraryType.current})
  }

  async getLibrary(owner: ExportifyUser, libraryId: string): Promise<WithId<Library> | null> {
    if (libraryId === LibraryType.current) {
      return await this.getCurrentLibrary(owner)
    }

    return await libraryCollection.findOne({_id: new ObjectId(libraryId)})
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