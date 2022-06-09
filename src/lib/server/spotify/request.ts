import {getSpotifyApi} from "../auth/spotify";
import {delay} from "../../shared/helpers";
import {DBClient} from "../db/client";
import type {ExportifyUser} from "../../shared/classes/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";
const clientId = process.env.VITE_SPOTIFY_CLIENT_ID || import.meta.env.VITE_SPOTIFY_CLIENT_ID
const clientSecret = process.env.VITE_SPOTIFY_CLIENT_SECRET || import.meta.env.VITE_SPOTIFY_CLIENT_SECRET

type requestFunc = (_: SpotifyWebApi) =>  any

async function makeRequest(exportifyUser: ExportifyUser, func: requestFunc) {

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

async function refreshAccessToken(exportifyUser: ExportifyUser) {
  const spotifyApi = getSpotifyApi(exportifyUser)
  spotifyApi.setClientSecret(clientSecret)
  spotifyApi.setClientId(clientId)
  const data = await rateLimitRequest(spotifyApi, async (api) => await api.refreshAccessToken())

  if (200 > data.statusCode && data.statusCode > 299) {
    return data
  }


  let newExportifyUser: ExportifyUser | null = exportifyUser
  newExportifyUser.access_token = data.body["access_token"]

  await DBClient.saveExportifyUser(newExportifyUser)
  newExportifyUser = await DBClient.getExportifyUser(exportifyUser.id)

  return {
    statusCode: 200,
    exportifyUser: newExportifyUser
  }
}

async function rateLimitRequest(spotifyApi: SpotifyWebApi, func: requestFunc) {
  let data = await request(spotifyApi, func)

  if (data.statusCode === 429) {
    const retry = data.headers["Retry-After"]
    await delay(retry * 1000)
    data = await request(spotifyApi, func)
  }
  return data;
}

async function request(spotifyApi: SpotifyWebApi, func: requestFunc) {
  try {
    return await func(spotifyApi)
  } catch (err) {
    return  err
  }
}

export {makeRequest}