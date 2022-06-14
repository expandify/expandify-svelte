import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';
import {LibraryItem, LibraryStatus, LibraryType} from "$lib/types/Library";
import type {Artist, LibraryArtist} from "$lib/types/Artist";
import {makeRequest} from "$lib/server/functions/request";
import type {ExportifyUser} from "$lib/types/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";
import {toArtist, toLibraryArtist} from "$lib/types/Artist";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params, url}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const compare_to = url.searchParams.get("compare-to")

  let compare: LibraryItem<LibraryArtist[]> | null = null
  if (compare_to !== null) {
    compare = await DBClient.getLibraryArtists(exportifyUser, compare_to)
  }
  const main = await DBClient.getLibraryArtists(exportifyUser, params?.library)
  const artists = _compareArtists(main, compare, params?.library, compare_to)

  return {
    status: artists.status,
    body: {
      libraryItem: artists
    }
  }
}

export const post: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) return {status: 403}
  if (params?.library !== LibraryType.current) return {status: 405}

  const exportifyUser = locals.exportifyUser
  await DBClient.updateCurrentLibraryArtists(exportifyUser, [], LibraryStatus.loading)
  _getArtists(exportifyUser).then()
  return {status: LibraryStatus.loading}
}

async function _getArtists(exportifyUser: ExportifyUser) {
  let after: string | null
  let next: string | null
  let artist: SpotifyApi.ArtistObjectFull[] = []
  do {
    let func = (api: SpotifyWebApi) => api.getFollowedArtists({limit: 50, ...(after && {after: after})})
    let data = await makeRequest(exportifyUser, func)
    let cursor = data.body
    after = cursor.artists.cursors.after
    next = cursor.artists.next
    artist = [...artist, ...cursor.artists.items]
  } while (next)
  await _save(artist, exportifyUser).then()
}

async function _save(savedArtists: SpotifyApi.ArtistObjectFull[], exportifyUser: ExportifyUser) {
  const artists: Artist[] = savedArtists.map(toArtist)
  const libraryArtist: LibraryArtist[] = savedArtists.map(toLibraryArtist)
  await DBClient.saveArtists(artists)
  await DBClient.updateCurrentLibraryArtists(exportifyUser, libraryArtist, LibraryStatus.ready)
}

function _compareArtists(main: LibraryItem<LibraryArtist[]>,
                        compare: LibraryItem<LibraryArtist[]> | null,
                        mainId: string,
                        compareId: string | null): LibraryItem<LibraryArtist[]> {
  if (compareId === null || compare === null) return main
  const result = new LibraryItem<LibraryArtist[]>(main.status, new Date().toString(), [])
  const mainIds = main.item.map(value => value.artist.id)
  const compareIds = compare.item.map(value => value.artist.id)
  const allArtists = [...main.item, ...compare.item]

  for (const artist of allArtists) {

    if (result.item.find(a => a.artist.id === artist.artist.id)) continue

    let id = artist.artist.id
    let inMain = mainIds.indexOf(id) !== -1
    let inCompare = compareIds.indexOf(id) !== -1
    artist.library = (inMain && inCompare) ? null : (inMain ? mainId : compareId);

    result.item.push(artist)
  }

  return result
}