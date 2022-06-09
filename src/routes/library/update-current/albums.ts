import {Library} from "../../../lib/shared/classes/Library";
import {unwrapPaging} from "../../../lib/server/spotify/paging";
import {DBClient} from "../../../lib/server/db/client";
import type {RequestHandler} from './__types/albums';

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryAlbums(exportifyUser, [], Library.Status.loading)

  unwrapPaging(exportifyUser, _getAlbums)
      .then(async items => {
        let albums = items.map(item => item.album)
        await DBClient.saveAlbums(albums)
        await DBClient.updateCurrentLibraryAlbums(exportifyUser, albums, Library.Status.ready)
      })

  return {status: Library.Status.loading}
}

async function _getAlbums(api: any, limit: number, offset: number) {
  return api.getMySavedAlbums({limit, offset});
}