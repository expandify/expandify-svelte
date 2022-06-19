import { DBClient } from "$lib/server/db/client";
import * as Spotify from "$lib/server/functions/request";
import { toArtist } from "$lib/types/Artist";
import type {RequestHandler} from './__types/[id]';

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser


  let dbArtist = await DBClient.getArtist(params.id)
  if (dbArtist && dbArtist.complete) {
    return {
      status: 200, 
      body: {artist: dbArtist}
    }
  }

  let reponse = await Spotify.makeRequest(exportifyUser, async (api) => await api.getArtist(params.id))
  const artist = toArtist(reponse.body)

  // Set Artist to complete

  DBClient.saveArtist(artist)

  return {
    status: reponse.statusCode,
    body: {
      artist: artist
    }
  }
}

