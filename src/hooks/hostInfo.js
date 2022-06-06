const HOST_NAME = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME

async function handle(event) {
  event.locals.BASE_URL = HOST_NAME
}

async function getSession(event) {
  return { BASE_URL: event.locals.BASE_URL }
}

export {handle, getSession}