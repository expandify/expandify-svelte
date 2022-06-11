import {unwrapPaging} from "$lib/server/spotify/paging";
import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/albums';
import type {Album} from "$lib/shared/types/Album";
import {LibraryStatus} from "$lib/shared/types/Library";
import type SpotifyWebApi from "spotify-web-api-node";

export const post: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser

  await DBClient.updateCurrentLibraryAlbums(exportifyUser, [], LibraryStatus.loading)

  unwrapPaging(exportifyUser, _getAlbums)
      .then(async items => {
        const albums: Album[] = items.map(value => value.album)
        await DBClient.saveAlbums(albums)
        await DBClient.updateCurrentLibraryAlbums(exportifyUser, albums, LibraryStatus.ready)
      })

  return {status: LibraryStatus.loading}
}

async function _getAlbums(api: SpotifyWebApi, limit: number, offset: number) {
  return api.getMySavedAlbums({limit, offset});
}