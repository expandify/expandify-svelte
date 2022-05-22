import { exportifyUserCollection, spotifyUserCollection} from "../../../lib/server/db/collections.js"
import db from "../../../lib/server/db/query.js";
import { createJwt} from "../../../lib/server/auth/jwt.js";
import { createCookie} from "../../../lib/server/auth/cookies.js";
import {authenticate, getSpotifyApi} from "../../../lib/server/auth/spotify.js";


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
      ...createCookie("jwt", token),
      location: "/"
    }
  }
}
