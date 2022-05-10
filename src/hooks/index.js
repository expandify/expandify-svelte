import * as cookie from "cookie"
import {verifyJwt} from "../lib/auth/jwt.js";
import {exportifyUserCollection } from "../lib/db/collections.js"

export async function handle({event, resolve}) {
  // This function gets called, everytime the server gets a request

  // See if a cookie exists
  const cookies = cookie.parse(event.request.headers.get("cookie") || "")

  // Decode the cookie and check if a valid user exists
  const decoded = verifyJwt(cookies.jwt);
  if (!!decoded) {
    try {
      // Set the user, token and loggedIn status
      // Usable in server endpoints
      event.locals.exportifyUser = await exportifyUserCollection.findOne({id: decoded.id})
      event.locals.loggedIn = !!event.locals.exportifyUser
      event.locals.jwt = cookies.jwt
    } catch (err) {
      event.locals.loggedIn = false
    }
  }



  // This calls the actual endpoint
  const response = await resolve(event)

  return response
}


export async function getSession(event) {
  // Sets the session. Usable in the client routes.
  // Since this is exposed to the client, no sensitive data should be set here.
  return {
    loggedIn: event.locals.loggedIn
  }
}

