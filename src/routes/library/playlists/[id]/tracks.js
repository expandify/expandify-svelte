import * as Spotify from "../../../../server/spotify.js";


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

  let playlistTracks = await Spotify.makeRequest(exportifyUser, async (api) => await api.getPlaylistTracks(params.id, options))

  return {
    status: playlistTracks.statusCode,
    body: {
      spotifyHeaders: playlistTracks.headers,
      playlistTracks: playlistTracks.body
    }
  }
}

