import { DBClient } from "$lib/server/db/client";
import * as Spotify from "$lib/server/functions/request";
import { toTrack } from "$lib/types/Track";
import type {RequestHandler} from './__types/[id]';

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser

  let dbTrack = await DBClient.getTrack(params.id)
  if (dbTrack && dbTrack.complete) {
    return {
      status: 200,
      body: {track: dbTrack}
    }
  }

  let response = await Spotify.makeRequest(exportifyUser, async (api) => await api.getTrack(params.id))
  const track = toTrack(response.body)


  // Get Artists
  // Set Track to complete
  
  DBClient.saveTrack(track)

  return {
    status: response.statusCode,
    body: {
      track: track
    }
  }
}

