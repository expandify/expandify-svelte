import {DBClient} from "../../../../lib/server/db/client";


export async function get({locals, params}) {
  if (!locals.loggedIn) {
    return {status: 403}
  }

  const exportifyUser = locals.exportifyUser
  const albums = await DBClient.getLibraryAlbums(exportifyUser, params?.library)

  return {
    status: albums.status,
    headers: {'Content-Type': 'application/json'},
    body: {
      items: albums.item,
      last_updated: albums.last_updated
    }
  }
}



