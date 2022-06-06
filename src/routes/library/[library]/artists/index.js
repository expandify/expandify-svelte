import {artistCollection} from "../../../../server/db/mongodb/collections.js";
import {unwrapCursor} from "../../../../server/spotify/paging";
import {Library} from "../../../../shared/classes/Library";
import {saveLibraryItems, updateLibraryItem} from "../../../../server/spotify/data.js";
import Query from "../../../../server/db/mongodb/query.js";

async function _getArtists(api, limit, after) {
  let options = {
    ...(limit) && {limit: limit},
    ...(after) && {after: after}
  }
  return api.getFollowedArtists(options);
}

export async function post({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const activeLibrary = await Query.getActiveLibrary(exportifyUser, params?.library)

  if (!activeLibrary || activeLibrary.type !== Library.Type.current) {
    return {status: 400}
  }

  await updateLibraryItem(activeLibrary._id, "followed_artists", Library.Status.loading, [])
  unwrapCursor(exportifyUser, _getArtists, (data) => data.artists)
      .then(async artists => await saveLibraryItems(artistCollection, activeLibrary._id, "followed_artists", artists))

  return {status: 202}
}


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const activeLibrary = await Query.getActiveLibrary(exportifyUser, params?.library)

  return await Query.getActiveLibraryItem(activeLibrary, artistCollection, "followed_artists")
}




