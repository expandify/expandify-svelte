import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';
import {LibraryStatus, LibraryType} from "$lib/types/Library";
import type {ExportifyUser} from "$lib/types/ExportifyUser";
import {makeRequest} from "../../../../lib/server/functions/request";
import {type Album, type LibraryAlbum, toAlbum, toLibraryAlbum} from "$lib/types/Album";
import type SpotifyWebApi from "spotify-web-api-node";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const albums = await DBClient.getLibraryAlbums(exportifyUser, params?.library)
  return {
    status: albums.status,
    body: {
      libraryItem: albums,
    }
  }
}

export const post: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) return {status: 403}
  if (params?.library !== LibraryType.current) return {status: 405}

  const exportifyUser = locals.exportifyUser
  await DBClient.updateCurrentLibraryAlbums(exportifyUser, [], LibraryStatus.loading)
  _getAlbums(exportifyUser).then()
  return {status: LibraryStatus.loading}
}

async function _getAlbums(exportifyUser: ExportifyUser) {
  let offset = 0
  let next: string | null
  let albums: SpotifyApi.SavedAlbumObject[] = []
  do {
    let func = (api: SpotifyWebApi) => api.getMySavedAlbums({limit: 50, offset})
    let data = await makeRequest(exportifyUser, func)
    let paging = data.body
    offset += paging.limit
    next = paging.next
    albums = [...albums, ...paging.items]
  } while (next)
  await _save(albums, exportifyUser).then()
}

async function _save(savedAlbums: SpotifyApi.SavedAlbumObject[], exportifyUser: ExportifyUser) {
  const albums: Album[] = savedAlbums.map(toAlbum)
  const libraryAlbums: LibraryAlbum[] = savedAlbums.map(toLibraryAlbum)
  await DBClient.saveAlbums(albums)
  await DBClient.updateCurrentLibraryAlbums(exportifyUser, libraryAlbums, LibraryStatus.ready)
}




