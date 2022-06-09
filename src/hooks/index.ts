import * as AuthHook from "./auth"
import * as MetricsHook from "./metrics"
import * as HostInfoHook from "./hostInfo"
import type {Handle, GetSession} from "@sveltejs/kit";

const HOST_NAME = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME


export const handle: Handle = async function ({event, resolve}) {
  // This function gets called, everytime the server gets a request

  await AuthHook.handle(event)
  await MetricsHook.handle(event)
  await HostInfoHook.handle(event)

  event.locals.BASE_URL = HOST_NAME

  return resolve(event);
}

export const getSession: GetSession = async function (event) {
  // Sets the session. Usable in the client routes.
  // Since this is exposed to the client, no sensitive data should be set here.
  let authSession = await AuthHook.getSession(event)
  let metricsSession = await MetricsHook.getSession(event)
  let hostInfoSession = await HostInfoHook.getSession(event)
  return {
    ...authSession,
    ...metricsSession,
    ...hostInfoSession
  }
}
