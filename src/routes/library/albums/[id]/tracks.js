import * as Spotify from "../../../../lib/server/spotify.js";


export async function get({locals, params, url}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const limit = url.searchParams.get("limit") || 50
  const offset = url.searchParams.get("offset") || 0

  const options = { limit: limit, offset: offset }
  const exportifyUser = locals.exportifyUser

  let albumTracks = await Spotify.makeRequest(exportifyUser, async (api) => await api.getAlbumTracks(params.id, options))

  return {
    status: albumTracks.statusCode,
    body: {
      spotifyHeaders: albumTracks.headers,
      albumTracks: albumTracks.body
    }
  }
}

