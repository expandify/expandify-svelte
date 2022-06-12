import type {RequestHandler} from './__types/login';
import SpotifyWebApi from "spotify-web-api-node";

const scopes = ["playlist-read-private", "playlist-read-collaborative", "user-library-read", "user-follow-read", "user-read-private"]
const hostname = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME
const spotifyRedirectEndpoint = process.env.VITE_SPOTIFY_REDIRECT_ENDPOINT || import.meta.env.VITE_SPOTIFY_REDIRECT_ENDPOINT
const redirectUri = `${hostname}${spotifyRedirectEndpoint}`
const clientId = process.env.VITE_SPOTIFY_CLIENT_ID || import.meta.env.VITE_SPOTIFY_CLIENT_ID
const state = randomString(20)
const showDialog = true


export const get: RequestHandler = async function (_) {
  // This is just a redirect to spotify login.
  // This has to happen in the backend, since sensitive data might be exposed otherwise

  const spotifyApi = new SpotifyWebApi({
    redirectUri: redirectUri,
    clientId: clientId
  });

  const authUrl = spotifyApi.createAuthorizeURL(scopes, state, showDialog);

  return {
    status: 302,
    headers: {
      location: authUrl
    }
  }
}

function randomString(length: number) {
  let result           = '';
  let characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let charactersLength = characters.length;
  for ( let i = 0; i < length; i++ ) {
    result += characters.charAt(Math.floor(Math.random() *
      charactersLength));
  }
  return result;
}