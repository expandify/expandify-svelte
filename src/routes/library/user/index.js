import {getSpotifyApi} from "../../../lib/server/auth/spotify.js";

export async function get({locals}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  if (locals.cookies.user === "cached") {
    return {
      status: 200,
      body: {
        items: null
      }
    }
  }

  const exportifyUser = locals.exportifyUser
  const data = (await getSpotifyApi(exportifyUser).getMe())

  return {
    status: 200,
    body: {
      user: data.body
    }
  }
}

