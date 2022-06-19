import { DBClient } from "$lib/server/db/client";
import * as Spotify from "$lib/server/functions/request";
import { toAlbum } from "$lib/types/Album";
import type {RequestHandler} from './__types/[id]';

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser

  let dbAlbum = await DBClient.getAlbum(params.id)    
  if (dbAlbum && dbAlbum.complete) {
    return {
      status: 200,
      body: {album: dbAlbum}
    }  
  }


  let response = await Spotify.makeRequest(exportifyUser, async (api) => await api.getAlbum(params.id))
  const album = toAlbum(response.body)

  // Get Album Tracks
  // Set album to complete 
  // (Do NOT set Tracks to complete)
  
  DBClient.saveAlbum(album)


  return {
    status: response.statusCode,
    body: {
      album: album
    }
  }
}

