export async function handle({ event, resolve }) {
  console.log("Event", event);
  console.log("Resolve", resolve)
  if (event.url.pathname.startsWith('/api/auth')) {
    console.log("api/auth hook found")
    return await resolve(event);
  }

  if (event.url.pathname.startsWith('/api')) {

    return Response.redirect("/", 401);
  }

  return await resolve(event);

}