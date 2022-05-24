import {getSpotifyApi} from "../../../lib/server/auth/spotify.js";


export async function get({locals, url}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }
  if (locals.cookies.artists === "cached") {
    return {
      status: 200,
      body: {
        items: null
      }
    }
  }


  const limit = url.searchParams.get("limit") || 50
  const after = url.searchParams.get("after")

  let options = {
    ...(limit) && {limit: limit},
    ...(after) && {after: after}
  }

  const exportifyUser = locals.exportifyUser
  const data = (await getSpotifyApi(exportifyUser).getFollowedArtists(options))

  return {
    status: 200,
    body: {
      items: data.body
    }
  }
}

