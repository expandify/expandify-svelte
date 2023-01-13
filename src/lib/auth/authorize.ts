import { Base64 } from 'js-base64';
import { PUBLIC_SPOTIFY_ID } from '$env/static/public';

import {authData} from "$lib/stores/authData"
import { createCodeChallenge, generateCodeVerifier } from './crypto';
import { goto } from '$app/navigation';

const AUTHORIZE_URL = "https://accounts.spotify.com/authorize"
const scopes = ["playlist-read-private", "playlist-read-collaborative", "user-library-read", "user-follow-read", "user-read-private"]
const redirect_uri = "http://localhost:5173/callback"


export async function authorize() {     
  // Safe codeVerifier and state to localStorage (authDate Store)
  // This is needed, cause the values for the token request have to be the same
  const codeVerifier = generateCodeVerifier();
  const state = Base64.encodeURI(generateCodeVerifier(20));
  authData.set({codeVerifier: codeVerifier, state: state});
  
  goto(await loginUrl(codeVerifier, state));
}

async function loginUrl(codeVerifier: string, state: string) {  
  
  const params = new URLSearchParams({
    response_type: 'code',
    client_id: PUBLIC_SPOTIFY_ID,
    redirect_uri: redirect_uri,
    code_challenge_method: 'S256',
    code_challenge: await createCodeChallenge(codeVerifier),
    state: state,
    scope: scopes.join(" "),
  });

  return `${AUTHORIZE_URL}?${params}`
}

