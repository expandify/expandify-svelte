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
        try {
          await Query.upsert_many(songCollection, items.map(item => item.track))
          return await updateLibraryItem(
              activeLibrary._id,
              "saved_tracks",
              Library.Status.ready,
              items.map(value => ({id: value.track.id, added_at: value.added_at})))
        } catch (err) {
          await updateLibraryItem(activeLibrary._id, "saved_tracks", Library.Status.error, [])
          throw new Error("Error updating Library.")
        }
      })

  return {status: 202}
}


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const activeLibrary = await Query.getActiveLibrary(exportifyUser, params?.library)


  if (!activeLibrary || activeLibrary["saved_tracks"].status === Library.Status.error) {
    return {status: 500}
  }
  if (activeLibrary["saved_tracks"].status === Library.Status.loading) {
    return {status: 202}
  }

  let libraryItems = activeLibrary["saved_tracks"].item || []
  const items = await Query.findAll(songCollection, {"id": {$in: libraryItems.map(value => value.id) || []}})


  const result = libraryItems.map(t1 => ({...t1, ...items.find(t2 => t2.id === t1.id)}))
  return {
    status: 200,
    body: {
      items: result
    }
  }

}



