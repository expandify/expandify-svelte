import { exportifyUserCollection, spotifyUserCollection} from "../../../server/db/collections.js"
import db from "../../../server/db/query.js";
import { createJwt} from "../../../server/auth/jwt.js";
import { createCookieHeader} from "../../../shared/cookies.js";
import {authenticate, getSpotifyApi} from "../../../server/auth/spotify.js";


export async function get(request) {

  const code = request.url.searchParams.get("code")

  const exportifyUser = await authenticate(code)
  const spotifyApi = getSpotifyApi(exportifyUser)

  const spotifyUser = (await spotifyApi.getMe()).body

  exportifyUser.id = spotifyUser.id

  await db.upsert(spotifyUserCollection, spotifyUser)
  await db.upsert(exportifyUserCollection, exportifyUser)


  const token = createJwt({id: spotifyUser.id})
  return {
    status: 302,
    headers: {
      ...createCookieHeader("jwt", token),
      location: "/"
    }
  }
}
