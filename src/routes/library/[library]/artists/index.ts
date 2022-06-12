import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';
import {LibraryStatus, LibraryType} from "../../../../lib/shared/types/Library";
import type {Artist, LibraryArtist} from "../../../../lib/shared/types/Artist";
import {makeRequest} from "../../../../lib/server/spotify/request";
import type {ExportifyUser} from "../../../../lib/shared/types/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";
import {toArtist, toLibraryArtist} from "../../../../lib/shared/types/Artist";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const artists = await DBClient.getLibraryArtists(exportifyUser, params?.library)

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