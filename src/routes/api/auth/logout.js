import {createCookie} from "../../../lib/server/auth/cookies.js";

export async function get(request) {

  return {
    status: 302,
    headers: {
      ...createCookie("jwt", null),
      location: "/"
    }
  }
}