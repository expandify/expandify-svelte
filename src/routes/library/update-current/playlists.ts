import {unwrapPaging} from "$lib/server/spotify/paging";
import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/albums';
import type {Playlist} from "$lib/shared/types/Playlist";
import {LibraryStatus} from "../../../lib/shared/types/Library";

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryPlaylists(exportifyUser, [], LibraryStatus.loading)

  unwrapPaging(exportifyUser, _getPlaylists)
    .then(async items => {
      const playlists: Playlist[] = items
      await DBClient.savePlaylists(playlists)
      await DBClient.updateCurrentLibraryPlaylists(exportifyUser, playlists, LibraryStatus.ready)
    })

  return {status: LibraryStatus.loading}
}

async function _getPlaylists(api: any, limit: number, offset: number) {
  return api.getUserPlaylists({limit, offset});
}