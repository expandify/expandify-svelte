import * as Spotify from "../../../../../lib/server/spotify/request";

export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser
  let track = await Spotify.makeRequest(exportifyUser, async (api) => await api.getTrack(params.id))

  return {
    status: track.statusCode,
    body: {
      track: track.body
    }
  }
}

