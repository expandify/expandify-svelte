import { createJwt} from "../../lib/server/functions/jwt";
import { createCookieHeader} from "../../lib/server/functions/cookies";
import {DBClient} from "$lib/server/db/client";
import type {ExportifyUser} from "$lib/types/ExportifyUser";
import SpotifyWebApi from "spotify-web-api-node";
import type {SpotifyUser} from "$lib/types/SpotifyUser";
import type {RequestHandler} from './__types/callback';

const hostname = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME
const spotifyRedirectEndpoint = process.env.VITE_SPOTIFY_REDIRECT_ENDPOINT || import.meta.env.VITE_SPOTIFY_REDIRECT_ENDPOINT
const redirectUri = `${hostname}${spotifyRedirectEndpoint}`
const clientId = process.env.VITE_SPOTIFY_CLIENT_ID || import.meta.env.VITE_SPOTIFY_CLIENT_ID
const clientSecret = process.env.VITE_SPOTIFY_CLIENT_SECRET || import.meta.env.VITE_SPOTIFY_CLIENT_SECRET

export const get: RequestHandler = async function (request) {
  const code = request.url.searchParams.get("code")

  if (code === null) throw new Error("Cannot Log In")

  const auth = await authenticate(code)
  const spotifyApi = new SpotifyWebApi()
  spotifyApi.setAccessToken(auth.access_token);
  spotifyApi.setRefreshToken(auth.refresh_token);

  const userProfile = (await spotifyApi.getMe()).body

  const exportifyUser: ExportifyUser = {...auth, ...userProfile}
  const spotifyUser: SpotifyUser = userProfile

  await DBClient.saveSpotifyUser(spotifyUser)
  await DBClient.saveExportifyUser(exportifyUser)

  await DBClient.initCurrentLibrary(exportifyUser)

  const token = createJwt({id: spotifyUser.id})
  return {
    status: 302,
    headers: {
      ...createCookieHeader("jwt", token),
      location: "/libraries/current/albums"
    }
  }
}

export async function authenticate(code: string) {

  const spotifyApi = new SpotifyWebApi({
    clientId: clientId,
    clientSecret: clientSecret,
    redirectUri: redirectUri
  });

  return (await spotifyApi.authorizationCodeGrant(code)).body
}