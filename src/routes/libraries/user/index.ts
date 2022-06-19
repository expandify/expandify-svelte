import * as Spotify from "$lib/server/functions/request";
import type {RequestHandler} from './__types';

// @ts-ignore
export const get: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {
    return {
      status: 403
    }
  }

  const exportifyUser = locals.exportifyUser
  const user = await Spotify.makeRequest(exportifyUser, async (api) => await api.getMe())

  return {
    status: user.statusCode,
    body: {
      user: user.body
    }
  }
}

