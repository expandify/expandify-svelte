import {getSpotifyApi} from "../../../lib/server/auth/spotify.js";

export async function get(request) {
  if (!request.locals.loggedIn) {
    return {
      status: 403
    }
  }

  const limit = request.url.searchParams.get("limit")
  const after = request.url.searchParams.get("after")

  let options = {
    ...(limit) && {limit: limit},
    ...(after) && {after: after}
  }

  const exportifyUser = request.locals.exportifyUser
  const data = (await getSpotifyApi(exportifyUser).getFollowedArtists(options))

  return {
    status: 200,
    body: data.body
  }
}