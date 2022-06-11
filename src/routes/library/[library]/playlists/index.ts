import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';

export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const playlists = await DBClient.getLibraryPlaylists(exportifyUser, params?.library)

  return {
    status: playlists.status,
    body: {
      items: JSON.stringify(playlists.item),
      last_updated: playlists.last_updated
    }
  }
}
