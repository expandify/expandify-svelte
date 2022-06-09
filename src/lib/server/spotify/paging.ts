import {Paging} from "../../shared/classes/Paging";

import {makeRequest} from "./request";
import {Cursor} from "../../shared/classes/Cursor";
import type {ExportifyUser} from "../../shared/classes/ExportifyUser";
import type SpotifyWebApi from "spotify-web-api-node";

async function unwrapPaging(exportifyUser: ExportifyUser, func: (spotifyApi: SpotifyWebApi, limit: number, offset: number) => any, limit = 50, offset = 0) {

  let data: any
  let paging: Paging<any> | null
  let items: any[] = []
  do {
    data = await makeRequest(exportifyUser, (spotifyApi) => func(spotifyApi, limit, offset))
    paging = Paging.from(data.body, (json) => json)
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
    cursor = Cursor.from(transform(data.body), (json) => json)
    after = cursor?.cursors?.after || after
    limit = cursor?.limit || limit
    items = [...items, ...cursor?.items || []]
  } while (!!cursor?.next)
  return items
}

export {unwrapPaging, unwrapCursor}