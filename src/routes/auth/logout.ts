import {createCookieHeader} from "../../lib/shared/cookies";
import type {RequestHandler} from './__types/logout';

export const get: RequestHandler = async function (_) {
  return {
    status: 302,
    headers: {
      ...createCookieHeader("jwt", null),
      location: "/"
    }
  }
}