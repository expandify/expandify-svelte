import {Library} from "../../../lib/shared/classes/Library";
import {unwrapPaging} from "../../../lib/server/spotify/paging";
import {DBClient} from "../../../lib/server/db/client";
import type {RequestHandler} from './__types/albums';

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryTracks(exportifyUser, [], Library.Status.loading)

  unwrapPaging(exportifyUser, _getTracks)
    .then(async items => {
      await DBClient.saveTracks(items.map(item => item.track))
      const libraryItems = items.map(value => ({...value.track, added_at: value.added_at}))
      await DBClient.updateCurrentLibraryTracks(exportifyUser, libraryItems, Library.Status.ready)
    })

  return {status: Library.Status.loading}
}

async function _getTracks(api: any, limit: number, offset: number) {
  return api.getMySavedTracks({limit, offset});
}