import * as cookie from "cookie"
import {verifyJwt} from "../server/auth/jwt.js";
import {exportifyUserCollection} from "../server/db/mongodb/collections.ts";

async function handle(event) {
  const cookies = cookie.parse(event.request.headers.get("cookie") || "")

  const decoded = verifyJwt(cookies.jwt);
  if (!!decoded) {
    try {
      event.locals.exportifyUser = await exportifyUserCollection.findOne({id: decoded.id})
      event.locals.loggedIn = !!event.locals.exportifyUser
      event.locals.jwt = cookies.jwt
      event.locals.cookies = cookies
    } catch (err) {
      event.locals.loggedIn = false
    }
  }
}

async function getSession(event) {
  return { loggedIn: event.locals.loggedIn }
}

export {handle, getSession}