import SpotifyWebApi from 'spotify-web-api-node';
import {randomString} from "../../shared/helpers";
import type {ExportifyUser} from "../../shared/classes/ExportifyUser";

const scopes = ["playlist-read-private", "playlist-read-collaborative", "user-library-read", "user-follow-read", "user-read-private"]


const hostname = process.env.VITE_HOST_NAME || import.meta.env.VITE_HOST_NAME
const spotifyRedirectEndpoint = process.env.VITE_SPOTIFY_REDIRECT_ENDPOINT || import.meta.env.VITE_SPOTIFY_REDIRECT_ENDPOINT

const redirectUri = `${hostname}${spotifyRedirectEndpoint}`



const clientId = process.env.VITE_SPOTIFY_CLIENT_ID || import.meta.env.VITE_SPOTIFY_CLIENT_ID
const clientSecret = process.env.VITE_SPOTIFY_CLIENT_SECRET || import.meta.env.VITE_SPOTIFY_CLIENT_SECRET
const state = randomString(20)
const showDialog = true


export function createAuthorizeURL() {

  const spotifyApi = new SpotifyWebApi({
    redirectUri: redirectUri,
    clientId: clientId
  });

   return  spotifyApi.createAuthorizeURL(scopes, state, showDialog);
}

export async function authenticate(code: string) {

  const spotifyApi = new SpotifyWebApi({
    clientId: clientId,
    clientSecret: clientSecret,
    redirectUri: redirectUri
  });

  return (await spotifyApi.authorizationCodeGrant(code)).body
}

export function getSpotifyApi(exportifyUser: ExportifyUser) {
  const spotifyApi = new SpotifyWebApi()
  spotifyApi.setAccessToken(exportifyUser.access_token);
  spotifyApi.setRefreshToken(exportifyUser.refresh_token);

  return spotifyApi
}