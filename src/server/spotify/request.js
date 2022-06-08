import {getSpotifyApi} from "../auth/spotify.js";
import {delay} from "../../shared/helpers.js";
import db from "../db/mongodb/query.js";
import {exportifyUserCollection} from "../db/mongodb/collections.ts";
const clientId = process.env.VITE_SPOTIFY_CLIENT_ID || import.meta.env.VITE_SPOTIFY_CLIENT_ID
const clientSecret = process.env.VITE_SPOTIFY_CLIENT_SECRET || import.meta.env.VITE_SPOTIFY_CLIENT_SECRET
async function makeRequest(exportifyUser, func = (_) => {}) {

  let spotifyApi = getSpotifyApi(exportifyUser)

  let data = await rateLimitRequest(spotifyApi, func)

  if (data.statusCode === 401) {
    const response = await refreshAccessToken(exportifyUser)
    if (response.statusCode !== 200) {
      return response
    }

    spotifyApi = getSpotifyApi(response.exportifyUser)
    data = await rateLimitRequest(spotifyApi, func)
  }

  return data
}

async function refreshAccessToken(exportifyUser) {
  const spotifyApi = getSpotifyApi(exportifyUser)
  spotifyApi.setClientSecret(clientSecret)
  spotifyApi.setClientId(clientId)
  const data = await rateLimitRequest(spotifyApi, async (api) => await api.refreshAccessToken())

  if (200 > data.statusCode && data.statusCode > 299) {
    return data
  }

  const new_access_token = {access_token: data.body["access_token"]}
  let newExportifyUser = exportifyUser
  try {
    await db.update(exportifyUserCollection, new_access_token, {id: exportifyUser.id})
    newExportifyUser = await exportifyUserCollection.findOne({id: exportifyUser.id})
  } catch (err) {
    return {
      statusCode: 500,
      message: "Error accessing the database."
    }
  }

  return {
    statusCode: 200,
    exportifyUser: newExportifyUser
  }
}

async function rateLimitRequest(spotifyApi, func = (_) => _) {
  let data = await request(spotifyApi, func)

  if (data.statusCode === 429) {
    const retry = data.headers["Retry-After"]
    await delay(retry * 1000)
    data = await request(spotifyApi, func)
  }
  return data;
}

async function request(spotifyApi, func = (_) => _) {
  try {
    return await func(spotifyApi)
  } catch (err) {
    return  err
  }
}

export {makeRequest}