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
        await DBClient.updateCurrentLibraryAlbums(exportifyUser, albums.map(value => value.id), Library.Status.ready)
      })

  return {status: 202}
}


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const activeLibrary = await DBClient.getLibrary(exportifyUser, params?.library)

  if (!activeLibrary || activeLibrary.saved_albums.status === Library.Status.error) {
    return {status: 500}
  }
  if (activeLibrary.saved_albums.status === Library.Status.loading) {
    return {status: 202}
  }

  const items = await DBClient.getLibraryAlbums(exportifyUser, params?.library)
  return {
    status: 200,
    body: {
      items: items
    }
  }

}



