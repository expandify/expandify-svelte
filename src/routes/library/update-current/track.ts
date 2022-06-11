import {unwrapPaging} from "$lib/server/spotify/paging";
import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/albums';
import type {Track} from "$lib/shared/types/Track";
import {LibraryStatus} from "$lib/shared/types/Library";

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryTracks(exportifyUser, [], LibraryStatus.loading)

  unwrapPaging(exportifyUser, _getTracks)
    .then(async items => {
      const tracks = items.map(value => ({...value.track, added_at: value.added_at})) as Track[]
      await DBClient.saveTracks(tracks)
      await DBClient.updateCurrentLibraryTracks(exportifyUser, tracks, LibraryStatus.ready)
    })

  return {status: LibraryStatus.loading}
}

async function _getTracks(api: any, limit: number, offset: number) {
  return api.getMySavedTracks({limit, offset});
}