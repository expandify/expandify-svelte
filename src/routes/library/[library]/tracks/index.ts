import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';
import {LibraryStatus, LibraryType} from "../../../../lib/shared/types/Library";
import type {ExportifyUser} from "../../../../lib/shared/types/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";
import {makeRequest} from "../../../../lib/server/spotify/request";
import {type LibraryTrack, toLibraryTrack, toTrack, type Track} from "../../../../lib/shared/types/Track";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const albums = await DBClient.getLibraryTracks(exportifyUser, params?.library)
  return {
    status: albums.status,
    body: {
      libraryItem: albums
    }
  }
}

export const post: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) return {status: 403}
  if (params?.library !== LibraryType.current) return {status: 405}

  const exportifyUser = locals.exportifyUser
  await DBClient.updateCurrentLibraryTracks(exportifyUser, [], LibraryStatus.loading)
  _getTracks(exportifyUser).then()
  return {status: LibraryStatus.loading}
}

async function _getTracks(exportifyUser: ExportifyUser) {
  let offset = 0
  let next: string | null
  let tracks: SpotifyApi.SavedTrackObject[] = []
  do {
    let func = (api: SpotifyWebApi) => api.getMySavedTracks({limit: 50, offset})
    let data = await makeRequest(exportifyUser, func)
    let paging = data.body
    offset += paging.limit
    next = paging.next
    tracks = [...tracks, ...paging.items]
  } while (next)
  await _save(tracks, exportifyUser).then()
}

async function _save(savedTracks:  SpotifyApi.SavedTrackObject[], exportifyUser: ExportifyUser) {
  const tracks: Track[] = savedTracks.map(toTrack)
  const libraryTracks: LibraryTrack[] = savedTracks.map(toLibraryTrack)
  await DBClient.saveTracks(tracks)
  await DBClient.updateCurrentLibraryTracks(exportifyUser, libraryTracks, LibraryStatus.ready)
}


