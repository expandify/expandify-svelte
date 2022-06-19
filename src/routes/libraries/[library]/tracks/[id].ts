import * as Spotify from "$lib/server/functions/request.js";
import type {RequestHandler} from './__types/[id]';

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
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

