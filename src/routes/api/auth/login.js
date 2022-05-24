import {createAuthorizeURL} from "../../../server/auth/spotify.js";

export async function get(request) {

  // This is just a redirect to spotify login.
  // This has to happen in the backend, since sensitive data might be exposed otherwise

  return {
    status: 302,
    headers: {
      location: createAuthorizeURL()
    }
  }
}