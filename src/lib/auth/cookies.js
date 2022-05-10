import cookie from "cookie";

export function createCookie(name, value) {

  const cookieArray = []

  cookieArray.push(cookie.serialize(name, value,
      {
        httpOnly: true,
        sameSite: "lax",
        maxAge: 60 * 60 * 24,
        path: "/"
      }
  ))

  return {
    "set-cookie": cookieArray
  }
}