import * as Spotify from "../../../../../server/spotify/request.js";

export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser
  let artist = await Spotify.makeRequest(exportifyUser, async (api) => await api.getArtist(params.id))

  return {
    status: artist.statusCode,
    body: {
      artist: artist.body
    }
  }
}

