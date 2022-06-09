import { createJwt} from "../../lib/server/auth/jwt";
import { createCookieHeader} from "../../lib/shared/cookies";
import {authenticate} from "../../lib/server/auth/spotify";
import {DBClient} from "../../lib/server/db/client";
import {ExportifyUser} from "../../lib/shared/classes/ExportifyUser";
import SpotifyWebApi from "spotify-web-api-node";
import {SpotifyUser} from "../../lib/shared/classes/SpotifyUser";
import type {RequestHandler} from './__types/callback';


export const get: RequestHandler = async function (request) {
  const code = request.url.searchParams.get("code")

  if (code === null) throw new Error("Cannot Log In")

  const auth = await authenticate(code)
  const spotifyApi = new SpotifyWebApi()
  spotifyApi.setAccessToken(auth.access_token);
  spotifyApi.setRefreshToken(auth.refresh_token);

  const userProfile = (await spotifyApi.getMe()).body

  const exportifyUser = new ExportifyUser({...auth, ...userProfile})
  const spotifyUser = new SpotifyUser(userProfile)

  await DBClient.saveSpotifyUser(spotifyUser)
  await DBClient.saveExportifyUser(exportifyUser)

  await DBClient.initCurrentLibrary(exportifyUser)

  const token = createJwt({id: spotifyUser.id})
  return {
    status: 302,
    headers: {
      ...createCookieHeader("jwt", token),
      location: "/library"
    }
  }
}
