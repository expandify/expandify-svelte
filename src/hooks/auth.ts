import * as cookie from "cookie"
import {verifyJwt} from "../lib/server/functions/jwt";
import {DBClient} from "$lib/server/db/client";
import type {RequestEvent} from "@sveltejs/kit";

async function handle(event: RequestEvent) {
  const cookies = cookie.parse(event.request.headers.get("cookie") || "")

  const decoded = verifyJwt(cookies.jwt);
  if (!!decoded) {
    try {
      event.locals.exportifyUser = await DBClient.getExportifyUser(decoded?.id)
      event.locals.loggedIn = !!event.locals.exportifyUser
      event.locals.jwt = cookies.jwt
      event.locals.cookies = cookies
    } catch (err) {
      event.locals.loggedIn = false
    }
  }
}

async function getSession(event: RequestEvent) {
  return { loggedIn: event.locals.loggedIn }
}

export {handle, getSession}