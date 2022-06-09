import type {RequestEvent} from "@sveltejs/kit";

const HOST_NAME = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME

async function handle(event: RequestEvent) {
  event.locals.BASE_URL = HOST_NAME
}

async function getSession(event: RequestEvent) {
  return { BASE_URL: event.locals.BASE_URL }
}

export {handle, getSession}