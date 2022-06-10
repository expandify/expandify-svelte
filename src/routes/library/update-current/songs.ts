import {Library} from "../../../lib/shared/classes/Library";
import {unwrapPaging} from "../../../lib/server/spotify/paging";
import {DBClient} from "../../../lib/server/db/client";
import type {RequestHandler} from './__types/albums';
import {Album} from "../../../lib/shared/classes/Album";
import {Track} from "../../../lib/shared/classes/Track";

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryTracks(exportifyUser, [], Library.Status.loading)

  unwrapPaging(exportifyUser, _getTracks)
    .then(async items => {
      const tracks = items
        .map(value => Track.from({...value.track, added_at: value.added_at}))
        .filter(x => !!x) as Track[]
      await DBClient.saveTracks(tracks)
      await DBClient.updateCurrentLibraryTracks(exportifyUser, tracks, Library.Status.ready)
    })

  return {status: Library.Status.loading}
}

async function _getTracks(api: any, limit: number, offset: number) {
  return api.getMySavedTracks({limit, offset});
}