import * as Spotify from "../../../lib/server/spotify.js";


export async function get({locals, url}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }
  // if (locals.cookies.albums === "cached") {
  //   return {
  //     status: 200,
  //     body: {
  //       items: null
  //     }
  //   }
  // }

  const limit = url.searchParams.get("limit") || 50
  const offset = url.searchParams.get("offset") || 0

  const exportifyUser = locals.exportifyUser
  const data = await Spotify.makeRequest(exportifyUser, async (api) => await api.getMySavedAlbums({ limit: limit, offset: offset }))

  return {
    status: data.statusCode,
    body: {
      spotifyHeaders: data.headers,
      items: data.body
    }
  }
}

