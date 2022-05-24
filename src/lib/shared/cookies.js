import cookie from "cookie";

export function createCookieHeader(name, value) {

  const cookieArray = []

  cookieArray.push(createCookieString(name, value))

  return {
    "set-cookie": cookieArray
  }
}

export function createCookieString(name, value, httpOnly = true) {
  return cookie.serialize(name, value,
      {
        httpOnly: httpOnly,
        sameSite: "lax",
        maxAge: 60 * 60 * 24,
        path: "/"
      }
  )
}