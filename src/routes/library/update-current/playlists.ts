import {Library} from "../../../lib/shared/classes/Library";
import {unwrapPaging} from "../../../lib/server/spotify/paging";
import {DBClient} from "../../../lib/server/db/client";
import type {RequestHandler} from './__types/albums';

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryPlaylists(exportifyUser, [], Library.Status.loading)

  unwrapPaging(exportifyUser, _getPlaylists)
    .then(async playlists => {
      await DBClient.savePlaylists(playlists)
      await DBClient.updateCurrentLibraryPlaylists(exportifyUser, playlists, Library.Status.ready)
    })

  return {status: Library.Status.loading}
}

async function _getPlaylists(api: any, limit: number, offset: number) {
  return api.getUserPlaylists({limit, offset});
}