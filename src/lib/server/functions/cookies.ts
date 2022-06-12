import cookie from "cookie";

export function createCookieHeader(name: string, value: string | null) {

  const cookieArray = []

  cookieArray.push(createCookieString(name, value))

  return {
    "set-cookie": cookieArray
  }
}

export function createCookieString(name: string, value: string | null, httpOnly = true) {
  return cookie.serialize(name, value || "null",
      {
        httpOnly: httpOnly,
        sameSite: "lax",
        maxAge: 60 * 60 * 24,
        path: "/"
      }
  )
}