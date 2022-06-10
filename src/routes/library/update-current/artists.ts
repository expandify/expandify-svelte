import {Library} from "../../../lib/shared/classes/Library";
import {unwrapCursor} from "../../../lib/server/spotify/paging";
import {DBClient} from "../../../lib/server/db/client";
import type {RequestHandler} from './__types/artists';
import {Artist} from "../../../lib/shared/classes/Artist";

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryArtists(exportifyUser, [], Library.Status.loading)

  unwrapCursor(exportifyUser, _getArtists, (data) => data.artists)
    .then(async items => {
      const artists = items.map(value => Artist.from(value)).filter(x => !!x) as Artist[]
      await DBClient.saveArtists(artists)
      await DBClient.updateCurrentLibraryArtists(exportifyUser, artists, Library.Status.ready)
    })

  return {status: Library.Status.loading}
}

async function _getArtists(api: any, limit: any, after: any) {
  let options = {
    ...(limit) && {limit: limit},
    ...(after) && {after: after}
  }
  return api.getFollowedArtists(options);
}