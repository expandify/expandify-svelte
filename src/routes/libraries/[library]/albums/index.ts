import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';
import {LibraryItem, LibraryStatus, LibraryType} from "$lib/types/Library";
import type {ExportifyUser} from "$lib/types/ExportifyUser";
import {makeRequest} from "../../../../lib/server/functions/request";
import {type Album, type LibraryAlbum, toAlbum, toLibraryAlbum} from "$lib/types/Album";
import type SpotifyWebApi from "spotify-web-api-node";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params, url}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const compare_to = url.searchParams.get("compare-to")

  let compare: LibraryItem<LibraryAlbum[]> | null = null
  if (compare_to !== null) {
    compare = await DBClient.getLibraryAlbums(exportifyUser, compare_to)
  }
  const main = await DBClient.getLibraryAlbums(exportifyUser, params?.library)
  const albums = _compareAlbums(main, compare, params?.library, compare_to)

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
  const albums: Album[] = savedAlbums.map(a => toAlbum(a.album))
  const libraryAlbums: LibraryAlbum[] = savedAlbums.map(toLibraryAlbum)
  await DBClient.saveAlbums(albums)
  await DBClient.updateCurrentLibraryAlbums(exportifyUser, libraryAlbums, LibraryStatus.ready)
}

function _compareAlbums(main: LibraryItem<LibraryAlbum[]>,
                        compare: LibraryItem<LibraryAlbum[]> | null,
                        mainId: string,
                        compareId: string | null): LibraryItem<LibraryAlbum[]> {
  if (compareId === null || compare === null) return main
  const result = new LibraryItem<LibraryAlbum[]>(main.status, new Date().toString(), [])
  const mainIds = main.item.map(value => value.album.id)
  const compareIds = compare.item.map(value => value.album.id)
  const allAlbums = [...main.item, ...compare.item]

  for (const album of allAlbums) {
    if (result.item.find(a => a.album.id === album.album.id)) continue

    let id = album.album.id
    let inMain = mainIds.indexOf(id) !== -1
    let inCompare = compareIds.indexOf(id) !== -1
    album.library = (inMain && inCompare) ? null : (inMain ? mainId : compareId);

    result.item.push(album)
  }

  return result
}



