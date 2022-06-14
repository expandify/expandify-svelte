import { DBClient } from "$lib/server/db/client";
import * as Spotify from "$lib/server/functions/request.js";
import { toAlbum, type Album } from "$lib/types/Album";
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
    let response: Album = dbAlbum

    return {
      status: 200,
      body: {
        album: response
      }
    }  
  }


  let response = await Spotify.makeRequest(exportifyUser, async (api) => await api.getAlbum(params.id))
  const album = toAlbum(response.body)

  DBClient.saveAlbum(album)


  return {
    status: response.statusCode,
    body: {
      album: album
    }
  }
}

