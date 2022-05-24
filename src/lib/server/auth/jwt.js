import JsonWebToken from "jsonwebtoken";

const jwtSecret = process.env.VITE_JWT_SECRET || import.meta.env.VITE_JWT_SECRET

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

