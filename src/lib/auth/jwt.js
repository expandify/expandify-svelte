import JsonWebToken from "jsonwebtoken";

const jwtSecret = import.meta.env.VITE_JWT_SECRET

export function createJwt(data) {
  return JsonWebToken.sign(data, jwtSecret)
}

export function verifyJwt(jwt) {
  try {
    return JsonWebToken.verify(jwt, jwtSecret);
  } catch(err) {
    return null
  }
}

