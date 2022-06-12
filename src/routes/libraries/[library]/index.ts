import type {RequestHandler} from './__types/index';
import {Library, LibrarySimplified, LibraryType} from "../../../lib/types/Library";
import {DBClient} from "../../../lib/server/db/client";

// @ts-ignore
export const get: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) return {status: 403}

  const exportifyUser = locals.exportifyUser
  const library = await DBClient.getLibrary(exportifyUser, params?.library)

  const libId = library.type === LibraryType.current ? LibraryType.current : library._id.toString()
  const librarySimplified = new LibrarySimplified(library, libId)
  return {
    status: 200,
    body: {
      library: librarySimplified
    }
  }
}


// @ts-ignore
export const post: RequestHandler = async function ({locals, params}) {
  if (!locals.loggedIn) return {status: 403}
  if (params?.library !== LibraryType.current) return {status: 405}

  const exportifyUser = locals.exportifyUser
  const currentLibrary = await DBClient.getCurrentLibrary(exportifyUser)
  const snapshotLibrary = Library.clone(currentLibrary)

  await DBClient.saveSnapshotLibrary(snapshotLibrary)
  return {status: 200}
}