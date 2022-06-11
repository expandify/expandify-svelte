import * as Spotify from "$lib/server/spotify/request.js";
import type {RequestHandler} from './__types/[id]';

export const get: RequestHandler = async function ({locals, params}) {
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


