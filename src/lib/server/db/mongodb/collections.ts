import getClient from "./connection";
import type {Album} from "$lib/shared/types/Album";
import type {Artist} from "$lib/shared/types/Artist";
import type {Library} from "$lib/shared/types/Library";
import type {Playlist} from "$lib/shared/types/Playlist";
import type {Track} from "$lib/shared/types/Track";
import type {ExportifyUser} from "$lib/shared/types/ExportifyUser";
import type {SpotifyUser} from "$lib/shared/types/SpotifyUser";

async function getDB() {
  const connection = await getClient()
  return connection.db("exportify-sveltekit")
}

const getAlbumCollection = async () => {
  return (await getDB()).collection<Album>("Album")
}

const getArtistCollection = async () => {
  return (await getDB()).collection<Artist>("Artist")
}

const getLibraryCollection = async () => {
  return (await getDB()).collection<Library>("Library")
}

const getPlaylistCollection = async () => {
  return (await getDB()).collection<Playlist>("Playlist")
}

const getSongCollection = async () => {
  return (await getDB()).collection<Track>("Track")
}

const getExportifyUserCollection = async () => {
  return (await getDB()).collection<ExportifyUser>("ExportifyUser")
}

const getSpotifyUserCollection = async () => {
  return (await getDB()).collection<SpotifyUser>("SpotifyUser")
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