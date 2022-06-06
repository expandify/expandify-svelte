import getClient from "./connection.js";

async function getDB() {
  const connection = await getClient()
  return connection.db("exportify-sveltekit")
}

const getAlbumCollection = async () => {
  return (await getDB()).collection("albums")
}

const getArtistCollection = async () => {
  return (await getDB()).collection("artists")
}

const getLibraryCollection = async () => {
  return (await getDB()).collection("libraries")
}

const getPlaylistCollection = async () => {
  return (await getDB()).collection("playlists")
}

const getSongCollection = async () => {
  return (await getDB()).collection("songs")
}

const getExportifyUserCollection = async () => {
  return (await getDB()).collection("exportifyUsers")
}

const getSpotifyUserCollection = async () => {
  return (await getDB()).collection("spotifyUsers")
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