import * as AuthHook from "./auth.js"
import * as MetricsHook from "./metrics.js"
import * as HostInfoHook from "./hostInfo.js"

const HOST_NAME = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME

export async function handle({event, resolve}) {
  // This function gets called, everytime the server gets a request

  await AuthHook.handle(event)
  await MetricsHook.handle(event)
  await HostInfoHook.handle(event)

  event.locals.BASE_URL = HOST_NAME

  return await resolve(event)
}


export async function getSession(event) {
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
