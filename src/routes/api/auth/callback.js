import SpotifyWebApi from 'spotify-web-api-node';
import { exportifyUserCollection, spotifyUserCollection} from "../../../lib/db/collections.js"
import {upsert} from "../../../lib/db/query.js";
import JsonWebToken from "jsonwebtoken";

const clientId = import.meta.env.VITE_SPOTIFY_CLIENT_ID
const clientSecret = import.meta.env.VITE_SPOTIFY_CLIENT_SECRET
const redirectUri = import.meta.env.VITE_SPOTIFY_REDIRECT_URI
const jwtSecret = import.meta.env.VITE_JWT_SECRET

export async function get(request) {

  const code = request.url.searchParams.get("code")

  const spotifyApi = new SpotifyWebApi({
    clientId: clientId,
    clientSecret: clientSecret,
    redirectUri: redirectUri
  });

// Retrieve an access token and a refresh token
  const exportifyUser = (await spotifyApi.authorizationCodeGrant(code)).body

  spotifyApi.setAccessToken(exportifyUser.access_token);
  spotifyApi.setRefreshToken(exportifyUser.refresh_token);

  const spotifyUser = (await spotifyApi.getMe()).body

  exportifyUser.id = spotifyUser.id

  await upsert(spotifyUserCollection, spotifyUser)
  await upsert(exportifyUserCollection, exportifyUser)


  const token = JsonWebToken.sign({id: spotifyUser.id}, jwtSecret)
  const json = JSON.stringify(spotifyUser.id);
  const value = Buffer.from(json).toString('base64');

  return {
    headers: {
      'set-cookie': `jwt=${value}; Path=/; HttpOnly`
    },
    body: spotifyUser
  };
}
