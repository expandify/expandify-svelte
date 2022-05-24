import * as Spotify from "../../../server/spotify.js";

export async function get({locals}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser
  const user = await Spotify.makeRequest(exportifyUser, async (api) => await api.getMe())

  return {
    status: user.statusCode,
    body: {
      spotifyHeaders: user.headers,
      user: user.body
    }
  }
}

