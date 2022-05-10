import {getSpotifyApi} from "../../../lib/auth/spotify.js";

export async function get(request) {
  if (!request.locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = request.locals.exportifyUser
  const data = (await getSpotifyApi(exportifyUser).getMe())

  return {
    status: 200,
    body: data.body
  }
}