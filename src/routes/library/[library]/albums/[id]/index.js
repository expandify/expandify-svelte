import * as Spotify from "../../../../../lib/server/spotify/request";


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser
  let album = await Spotify.makeRequest(exportifyUser, async (api) => await api.getAlbum(params.id))



  return {
    status: album.statusCode,
    body: {
      album: album.body
    }
  }
}

