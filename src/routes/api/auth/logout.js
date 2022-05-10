import {createCookie} from "../../../lib/auth/cookies.js";

export async function get(request) {

  return {
    status: 302,
    headers: {
      ...createCookie("jwt", null),
      location: "/"
    }
  }
}