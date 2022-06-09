import {createAuthorizeURL} from "../../lib/server/auth/spotify";
import type {RequestHandler} from './__types/login';

export const get: RequestHandler = async function (_) {
  // This is just a redirect to spotify login.
  // This has to happen in the backend, since sensitive data might be exposed otherwise

  return {
    status: 302,
    headers: {
      location: createAuthorizeURL()
    }
  }
}
