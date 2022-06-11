import {unwrapCursor} from "$lib/server/spotify/paging";
import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/artists';
import type {Artist} from "$lib/shared/types/Artist";
import {LibraryStatus} from "$lib/shared/types/Library";

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryArtists(exportifyUser, [], LibraryStatus.loading)

  unwrapCursor(exportifyUser, _getArtists, (data) => data.artists)
    .then(async items => {
      const artists: Artist[] = items
      await DBClient.saveArtists(artists)
      await DBClient.updateCurrentLibraryArtists(exportifyUser, artists, LibraryStatus.ready)
    })

  return {status: LibraryStatus.loading}
}

async function _getArtists(api: any, limit: any, after: any) {
  let options = {
    ...(limit) && {limit: limit},
    ...(after) && {after: after}
  }
  return api.getFollowedArtists(options);
}