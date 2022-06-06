import {albumCollection} from "../../../../server/db/mongodb/collections.js";
import {unwrapPaging} from "../../../../server/spotify/paging.ts";
import {Library} from "../../../../shared/classes/Library.ts";
import {saveLibraryItems, updateLibraryItem} from "../../../../server/spotify/data.js";
import Query from "../../../../server/db/mongodb/query.js";

async function _getAlbums(api, limit, offset) {
  return api.getMySavedAlbums({limit, offset});
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

  await updateLibraryItem(activeLibrary._id, "saved_albums", Library.Status.loading, [])
  unwrapPaging(exportifyUser, _getAlbums)
      .then(async items => {
        let albums = items.map(item => item.album)
        await saveLibraryItems(albumCollection, activeLibrary._id, "saved_albums", albums)
      })

  return {status: 202}
}


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const activeLibrary = await Query.getActiveLibrary(exportifyUser, params?.library)

  return await Query.getActiveLibraryItem(activeLibrary, albumCollection, "saved_albums")
}



