import {getSpotifyApi} from "../../../lib/server/auth/spotify.js";

export async function get(request) {
  if (!request.locals.loggedIn) {
    return {
      status: 403
    }
  }

  const limit = request.url.searchParams.get("limit")
  const offset = request.url.searchParams.get("offset")

  const exportifyUser = request.locals.exportifyUser
  const data = (await getSpotifyApi(exportifyUser).getUserPlaylists({ limit: limit, offset: offset }))

  return {
    status: 200,
    body: data.body
  }
}