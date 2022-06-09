import {DBClient} from "../../../../lib/server/db/client";

export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }
  const exportifyUser = locals.exportifyUser
  const artists = await DBClient.getLibraryArtists(exportifyUser, params?.library)

  return {
    status: artists.status,
    headers: {'Content-Type': 'application/json'},
    body: {
      items: artists.item,
      last_updated: artists.last_updated
    }
  }
}
