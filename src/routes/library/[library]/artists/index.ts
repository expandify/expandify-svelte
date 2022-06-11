import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const artists = await DBClient.getLibraryArtists(exportifyUser, params?.library)

  return {
    status: artists.status,
    body: {
      items: artists.item,
      last_updated: artists.last_updated
    }
  }
}