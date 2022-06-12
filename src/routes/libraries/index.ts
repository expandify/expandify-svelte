import type {RequestHandler} from './__types/index';
import {DBClient} from "../../lib/server/db/client";
import {LibrarySimplified} from "../../lib/types/Library";

// @ts-ignore
export const get: RequestHandler = async function ({locals}) {
  if (!locals.loggedIn) {return {status: 403}}

  const exportifyUser = locals.exportifyUser
  const libraries = await DBClient.getAllUserLibraries(exportifyUser)
  const librariesSimplified = libraries.map(lib => new LibrarySimplified(lib, lib._id.toString()))

  return {
    status: 200,
    body: {
      libraries: librariesSimplified
    }
  }
}