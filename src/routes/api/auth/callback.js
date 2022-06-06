import {
  exportifyUserCollection,
  libraryCollection,
  spotifyUserCollection
} from "../../../server/db/mongodb/collections.js"
import db from "../../../server/db/mongodb/query.js";
import { createJwt} from "../../../server/auth/jwt.js";
import { createCookieHeader} from "../../../shared/cookies.js";
import {authenticate, getSpotifyApi} from "../../../server/auth/spotify.js";
import {Library, LibraryItem} from "../../../shared/classes/Library";


export async function get(request) {

  const code = request.url.searchParams.get("code")

  const exportifyUser = await authenticate(code)
  const spotifyApi = getSpotifyApi(exportifyUser)

  const spotifyUser = (await spotifyApi.getMe()).body

  exportifyUser.id = spotifyUser.id

  await db.upsert(spotifyUserCollection, spotifyUser)
  await db.upsert(exportifyUserCollection, exportifyUser)

  const currentLibrary = new Library()
  currentLibrary.type = Library.Type.current
  currentLibrary.owner = new LibraryItem(Library.Status.ready, new Date().toString(), spotifyUser.id)

  await libraryCollection.insertOne(currentLibrary)

  const token = createJwt({id: spotifyUser.id})
  return {
    status: 302,
    headers: {
      ...createCookieHeader("jwt", token),
      location: "/library/libraries"
    }
  }
}
