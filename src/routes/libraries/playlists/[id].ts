import { DBClient } from "$lib/server/db/client";
import * as Spotify from "$lib/server/functions/request";
import { toPlaylist } from "$lib/types/Playlist";
import type {RequestHandler} from './__types/[id]';

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser

  let dbPlaylist = await DBClient.getPlaylist(params.id)
  if (dbPlaylist && dbPlaylist.complete) {
    return {
      status: 200,
      body: {playlist: dbPlaylist}
    }
  }

  let response = await Spotify.makeRequest(exportifyUser, async (api) => await api.getPlaylist(params.id))
  const playlist = toPlaylist(response.body)

  // Get Playlist Tracks
  // Set Playlist to complete
  // (Do NOT set Tracks to complete)

  DBClient.savePlaylist(playlist)


  return {
    status: response.statusCode,
    body: {
      playlist: playlist
    }
  }
}

