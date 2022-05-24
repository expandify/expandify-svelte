import {getSpotifyApi} from "../../../lib/server/auth/spotify.js";


export async function get({locals, url}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }
  if (locals.cookies.albums === "cached") {
    return {
      status: 200,
      body: {
        items: null
      }
    }
  }


  const limit = url.searchParams.get("limit") || 50
  const offset = url.searchParams.get("offset") || 0

  const exportifyUser = locals.exportifyUser
  const data = (await getSpotifyApi(exportifyUser).getMySavedAlbums({ limit: limit, offset: offset }))

  return {
    status: 200,
    body: {
      items: data.body
    }
  }
}

