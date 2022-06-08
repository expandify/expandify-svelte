import {unwrapPaging} from "../../../../server/spotify/paging.ts";
import {Library} from "../../../../shared/classes/Library.ts";
import {DBClient} from "../../../../server/db/client.ts";


async function _getAlbums(api, limit, offset) {
  return api.getMySavedAlbums({limit, offset});
}

export async function post({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const library = await DBClient.getLibrary(exportifyUser, params?.library)

  if (!library || library.type !== Library.Type.current) {
    return {status: 400}
  }

  await DBClient.updateCurrentLibraryAlbums(exportifyUser, [], Library.Status.loading)

  unwrapPaging(exportifyUser, _getAlbums)
      .then(async items => {
        let albums = items.map(item => item.album)
        await DBClient.saveAlbums(albums)
        await DBClient.updateCurrentLibraryAlbums(exportifyUser, albums, Library.Status.ready)
      })

  return {status: Library.Status.loading}
}


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const albums = await DBClient.getLibraryAlbums(exportifyUser, params?.library)

  return {
    status: albums.status,
    body: {
      items: albums.item,
      last_updated: albums.last_updated
    }
  }
}



