import crypto from "crypto";
import SpotifyWebApi from 'spotify-web-api-node';

const scopes = ["playlist-read-private", "playlist-read-collaborative", "user-library-read", "user-follow-read", "user-read-private"]
const redirectUri = import.meta.env.VITE_HOST_NAME + import.meta.env.VITE_SPOTIFY_REDIRECT_ENDPOINT
const clientId = import.meta.env.VITE_SPOTIFY_CLIENT_ID
const clientSecret = import.meta.env.VITE_SPOTIFY_CLIENT_SECRET
const state = crypto.randomBytes(20).toString("hex")
const showDialog = true


export function createAuthorizeURL() {

  const spotifyApi = new SpotifyWebApi({
    redirectUri: redirectUri,
    clientId: clientId
  });

    // This function exists, but it is loaded at runtime, so the IDE doesn't recognice it.
   return  spotifyApi.createAuthorizeURL(scopes, state, showDialog);
}

export async function authenticate(code) {

  const spotifyApi = new SpotifyWebApi({
    clientId: clientId,
    clientSecret: clientSecret,
    redirectUri: redirectUri
  });

  // This function exists, but it is loaded at runtime, so the IDE doesn't recognice it.
  return (await spotifyApi.authorizationCodeGrant(code)).body
}

export function getSpotifyApi(exportifyUser) {
  const spotifyApi = new SpotifyWebApi()
  spotifyApi.setAccessToken(exportifyUser.access_token);
  spotifyApi.setRefreshToken(exportifyUser.refresh_token);

  return spotifyApi
}