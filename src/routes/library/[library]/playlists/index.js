import {playlistCollection} from "../../../../server/db/mongodb/collections.js";
import {unwrapPaging} from "../../../../server/spotify/paging.ts";
import {Library} from "../../../../shared/classes/Library.ts";
import {saveLibraryItems, updateLibraryItem} from "../../../../server/spotify/data.js";
import Query from "../../../../server/db/mongodb/query.js";

async function _getPlaylists(api, limit, offset) {
  return api.getUserPlaylists({limit, offset});
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

  await updateLibraryItem(activeLibrary._id, "playlists", Library.Status.loading, [])
  unwrapPaging(exportifyUser, _getPlaylists)
      .then(async playlists => {
        await saveLibraryItems(playlistCollection, activeLibrary._id, "playlists", playlists)
      })

  return {status: 202}
}


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const activeLibrary = await Query.getActiveLibrary(exportifyUser, params?.library)

  return await Query.getActiveLibraryItem(activeLibrary, playlistCollection, "playlists")
}



