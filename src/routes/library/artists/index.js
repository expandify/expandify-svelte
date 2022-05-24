import * as Spotify from "../../../server/spotify.js";


export async function get({locals, url}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }


  const limit = url.searchParams.get("limit") || 50
  const after = url.searchParams.get("after")

  let options = {
    ...(limit) && {limit: limit},
    ...(after) && {after: after}
  }

  const exportifyUser = locals.exportifyUser
  const data = await Spotify.makeRequest(exportifyUser, async (api) => await api.getFollowedArtists(options))

  return {
    status: data.statusCode,
    body: {
      spotifyHeaders: data.headers,
      items: data.body
    }
  }

}

