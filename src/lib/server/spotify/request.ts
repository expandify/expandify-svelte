import {getSpotifyApi} from "../auth/spotify";
import {delay} from "../../shared/helpers";
import {DBClient} from "../db/client";
import type {ExportifyUser} from "../../shared/types/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";

const clientId = process.env.VITE_SPOTIFY_CLIENT_ID || import.meta.env.VITE_SPOTIFY_CLIENT_ID
const clientSecret = process.env.VITE_SPOTIFY_CLIENT_SECRET || import.meta.env.VITE_SPOTIFY_CLIENT_SECRET

type Response<T> = {
  body: T;
  headers: Record<string, string>;
  statusCode: number;
}
type RequestFunc<T> = (api: SpotifyWebApi) => Promise<Response<T>>

async function makeRequest<T>(exportifyUser: ExportifyUser, func: RequestFunc<T>): Promise<Response<T>> {

  let spotifyApi = getSpotifyApi(exportifyUser)

  let data = await rateLimitRequest(spotifyApi, func)

  if (data.statusCode === 401) {
    const response = await refreshAccessToken(exportifyUser)
    if (response.statusCode !== 200 || response.exportifyUser === null) {
      return Promise.reject(response)
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
    return {statusCode: data.statusCode, exportifyUser: null}
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

async function rateLimitRequest<T>(spotifyApi: SpotifyWebApi, func: RequestFunc<T>): Promise<Response<T>> {
  try{
    let data = await func(spotifyApi)
    if (data.statusCode === 429) {
      const retry = data.headers["Retry-After"]
      await delay(Number(retry) * 1000)
      data = await func(spotifyApi)
    }
    return data;
  } catch (err: any) {
    return {
      statusCode: err.body.error.status,
      body: err.body,
      headers: err.headers
    }
  }
}

export {makeRequest}