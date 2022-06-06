import {songCollection} from "../../../../server/db/mongodb/collections.js";
import {unwrapPaging} from "../../../../server/spotify/paging.ts";
import {Library} from "../../../../shared/classes/Library.ts";
import {saveLibraryItems, updateLibraryItem} from "../../../../server/spotify/data.js";
import Query from "../../../../server/db/mongodb/query.js";

async function _getTracks(api, limit, offset) {
  return api.getMySavedTracks({limit, offset});
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

  await updateLibraryItem(activeLibrary._id, "saved_tracks", Library.Status.loading, [])
  unwrapPaging(exportifyUser, _getTracks)
      .then(async items => {
        let tracks = items.map(item => item.track)
        await saveLibraryItems(songCollection, activeLibrary._id, "saved_tracks", tracks)
      })

  return {status: 202}
}


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const activeLibrary = await Query.getActiveLibrary(exportifyUser, params?.library)


  return await Query.getActiveLibraryItem(activeLibrary, songCollection, "saved_tracks")
}



