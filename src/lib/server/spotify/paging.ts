import type {Paging} from "../../shared/types/Paging";

import {makeRequest} from "./request";
import type {Cursor} from "../../shared/types/Cursor";
import type {ExportifyUser} from "../../shared/types/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";
import type {Artist} from "../../shared/types/Artist";

async function unwrapPaging(exportifyUser: ExportifyUser, func: (spotifyApi: SpotifyWebApi, limit: number, offset: number) => any, limit = 50, offset = 0) {

  let data: any
  let paging: Paging<any> | null
  let items: any[] = []
  do {
    data = await makeRequest(exportifyUser, (spotifyApi) => func(spotifyApi, limit, offset))
    paging = data.body
    limit = paging?.limit || limit
    offset += limit
    items = [...items, ...paging?.items || []]
  } while (!!paging?.next)

  return items
}

async function unwrapCursor(
  exportifyUser: ExportifyUser,
  func: (spotifyApi: any, limit: number, after: string | null) => any,
  transform: (data: any) => any = (x) => x,
  limit = 50,
  after: string | null = null) {

  let data: any
  let cursor: Cursor<any> | null
  let items: any[] = []
  do {
    data = await makeRequest(exportifyUser, (spotifyApi) => func(spotifyApi, limit, after))
    cursor = transform(data.body) as Cursor<Artist>
    after = cursor?.cursors?.after || after
    limit = cursor?.limit || limit
    items = [...items, ...cursor?.items || []]
  } while (!!cursor?.next)
  return items
}

export {unwrapPaging, unwrapCursor}