import {DBClient} from "$lib/server/db/client";
import type {RequestHandler} from './__types/index';
import {LibraryItem, LibraryStatus, LibraryType} from "$lib/types/Library";
import type {ExportifyUser} from "$lib/types/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";
import {makeRequest} from "$lib//server/functions/request";
import {type LibraryTrack, toLibraryTrack, toTrack, type Track} from "$lib/types/Track";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params, url}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const compare_to = url.searchParams.get("compare-to")

  let compare: LibraryItem<LibraryTrack[]> | null = null
  if (compare_to !== null) {
    compare = await DBClient.getLibraryTracks(exportifyUser, compare_to)
  }

  const main = await DBClient.getLibraryTracks(exportifyUser, params?.library)
  const tracks = _compareTracks(main, compare, params?.library, compare_to)
  return {
    status: tracks.status,
    body: {
      libraryItem: tracks
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
  const tracks: Track[] = savedTracks.map(t => toTrack(t.track))
  const libraryTracks: LibraryTrack[] = savedTracks.map(toLibraryTrack)
  await DBClient.saveTracks(tracks)
  await DBClient.updateCurrentLibraryTracks(exportifyUser, libraryTracks, LibraryStatus.ready)
}

function _compareTracks(main: LibraryItem<LibraryTrack[]>,
                         compare: LibraryItem<LibraryTrack[]> | null,
                         mainId: string,
                         compareId: string | null): LibraryItem<LibraryTrack[]> {
  if (compareId === null || compare === null) return main
  const result = new LibraryItem<LibraryTrack[]>(main.status, new Date().toString(), [])
  const mainIds = main.item.map(value => value.track.id)
  const compareIds = compare.item.map(value => value.track.id)
  const allTracks = [...main.item, ...compare.item]

  for (const track of allTracks) {

    if (result.item.find(a => a.track.id === track.track.id)) continue

    let id = track.track.id
    let inMain = mainIds.indexOf(id) !== -1
    let inCompare = compareIds.indexOf(id) !== -1
    track.library = (inMain && inCompare) ? null : (inMain ? mainId : compareId);

    result.item.push(track)
  }

  return result
}
