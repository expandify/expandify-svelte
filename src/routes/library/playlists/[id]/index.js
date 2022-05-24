import * as Spotify from "../../../../lib/server/spotify.js";


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser
  let playlist = await Spotify.makeRequest(exportifyUser, async (api) => await api.getPlaylist(params.id))



  return {
    status: playlist.statusCode,
    body: {
      spotifyHeaders: playlist.headers,
      playlist: playlist.body
    }
  }
}

