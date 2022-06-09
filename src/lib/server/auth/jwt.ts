import JsonWebToken from "jsonwebtoken";

const jwtSecret = process.env.VITE_JWT_SECRET || import.meta.env.VITE_JWT_SECRET

export function createJwt(data: {id: string}) {
  return JsonWebToken.sign(data, jwtSecret)
}

export function verifyJwt(jwt: string): JsonWebToken.JwtPayload | null {
  try {
    return JsonWebToken.verify(jwt, jwtSecret) as JsonWebToken.JwtPayload;
  } catch(err) {
    return null
  }
}

