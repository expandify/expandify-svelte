import crypto from "crypto";
import SpotifyWebApi from 'spotify-web-api-node';

const scopes = ["playlist-read-private", "playlist-read-collaborative", "user-library-read", "user-follow-read", "user-read-private"]
const redirectUri = import.meta.env.VITE_SPOTIFY_REDIRECT_URI
const clientId = import.meta.env.VITE_SPOTIFY_CLIENT_ID
const state = crypto.randomBytes(20).toString("hex")

export async function get(request) {
  const spotifyApi = new SpotifyWebApi({
    redirectUri: redirectUri,
    clientId: clientId
  });

  // This function exists, but it is dynamically loaded, so WebStorm doesn't recognice it.
  const authorizeURL = spotifyApi.createAuthorizeURL(scopes, state);

  return {
    status: 302,
    headers: {
      location: authorizeURL
    }
  }
}