import * as cookie from "cookie"
import {verifyJwt} from "../server/auth/jwt.js";
import {exportifyUserCollection} from "../server/db/collections.js"
import {collectDefaultMetrics, Counter, register} from "prom-client"


const HOST_NAME = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME

register.clear()
collectDefaultMetrics();

const requests = new Counter({
  name: "exportify_requests_total",
  help: "Requests made to Exportify",
  labelNames: ["requests", "method", "path"]
});


export async function handle({event, resolve}) {
  // This function gets called, everytime the server gets a request

  await decodeJwtCookie(event)
  event.locals.BASE_URL = HOST_NAME

  requests.inc()
  requests.inc({method: event.request.method, path: event.url.pathname})

  // This calls the actual endpoint
  return await resolve(event)
}


export async function getSession(event) {
  // Sets the session. Usable in the client routes.
  // Since this is exposed to the client, no sensitive data should be set here.
  return {
    loggedIn: event.locals.loggedIn,
    BASE_URL: event.locals.BASE_URL
  }
}

async function decodeJwtCookie(event) {
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
