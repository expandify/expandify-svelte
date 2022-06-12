import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';
import {LibraryStatus, LibraryType} from "../../../../lib/types/Library";
import type {ExportifyUser} from "../../../../lib/types/ExportifyUser";
import {makeRequest} from "../../../../lib/server/functions/request";
import {type LibraryPlaylist, type Playlist, toLibraryPlaylist, toPlaylist} from "../../../../lib/types/Playlist";
import type SpotifyWebApi from "spotify-web-api-node";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const playlists = await DBClient.getLibraryPlaylists(exportifyUser, params?.library)

  return {
    status: playlists.status,
    body: {
      libraryItem: playlists
    }
  }
}


export const post: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) return {status: 403}
  if (params?.library !== LibraryType.current) return {status: 405}

  const exportifyUser = locals.exportifyUser
  await DBClient.updateCurrentLibraryPlaylists(exportifyUser, [], LibraryStatus.loading)
  _getPlaylists(exportifyUser).then()
  return {status: LibraryStatus.loading}
}

async function _getPlaylists(exportifyUser: ExportifyUser) {
  let offset = 0
  let next: string | null
  let playlists: SpotifyApi.PlaylistObjectSimplified[] = []
  do {
    let func = (api: SpotifyWebApi) => api.getUserPlaylists({limit: 50, offset})
    let data = await makeRequest(exportifyUser, func)
    let paging = data.body
    offset += paging.limit
    next = paging.next
    playlists = [...playlists, ...paging.items]
  } while (next)
  await _save(playlists, exportifyUser).then()
}

async function _save(playlistObjects: SpotifyApi.PlaylistObjectSimplified[], exportifyUser: ExportifyUser) {
  const playlists: Playlist[] = playlistObjects.map(toPlaylist)
  const libraryPlaylists: LibraryPlaylist[] = playlistObjects.map(toLibraryPlaylist)
  await DBClient.savePlaylists(playlists)
  await DBClient.updateCurrentLibraryPlaylists(exportifyUser, libraryPlaylists, LibraryStatus.ready)
}