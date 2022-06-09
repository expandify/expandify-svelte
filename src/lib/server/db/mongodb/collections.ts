import getClient from "./connection";
import {Album} from "../../../shared/classes/Album";
import {Artist} from "../../../shared/classes/Artist";
import {Library} from "../../../shared/classes/Library";
import {Playlist} from "../../../shared/classes/Playlist";
import {Track} from "../../../shared/classes/Track";
import {ExportifyUser} from "../../../shared/classes/ExportifyUser";
import {SpotifyUser} from "../../../shared/classes/SpotifyUser";

async function getDB() {
  const connection = await getClient()
  return connection.db("exportify-sveltekit")
}

const getAlbumCollection = async () => {
  return (await getDB()).collection<Album>(Album.name)
}

const getArtistCollection = async () => {
  return (await getDB()).collection<Artist>(Artist.name)
}

const getLibraryCollection = async () => {
  return (await getDB()).collection<Library>(Library.name)
}

const getPlaylistCollection = async () => {
  return (await getDB()).collection<Playlist>(Playlist.name)
}

const getSongCollection = async () => {
  return (await getDB()).collection<Track>(Track.name)
}

const getExportifyUserCollection = async () => {
  return (await getDB()).collection<ExportifyUser>(ExportifyUser.name)
}

const getSpotifyUserCollection = async () => {
  return (await getDB()).collection<SpotifyUser>(SpotifyUser.name)
}

const spotifyUserCollection = await getSpotifyUserCollection()
const exportifyUserCollection = await getExportifyUserCollection()
const albumCollection = await getAlbumCollection()
const artistCollection = await getArtistCollection()
const libraryCollection = await getLibraryCollection()
const playlistCollection = await getPlaylistCollection()
const songCollection = await getSongCollection()

export {
  albumCollection,
  artistCollection,
  libraryCollection,
  playlistCollection,
  songCollection,
  exportifyUserCollection,
  spotifyUserCollection
}