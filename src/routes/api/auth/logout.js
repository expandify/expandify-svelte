import {createCookieHeader} from "../../../shared/cookies.js";

export async function get(request) {

  return {
    status: 302,
    headers: {
      ...createCookieHeader("jwt", null),
      location: "/"
    }
  }
}