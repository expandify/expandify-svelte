import { Base64 } from 'js-base64';
import { PUBLIC_SPOTIFY_ID } from '$env/static/public';

import { setCodeVerifier, setState, setTokenExpires, setToken, setRefreshToken, setTokenScope, authData, clearAuthData } from "$lib/stores/authData"
import { createCodeChallenge, generateCodeVerifier } from './crypto';
import { get } from 'svelte/store';
import { error } from '@sveltejs/kit';

const AUTHORIZE_URL = "https://accounts.spotify.com/authorize"
const TOKEN_API_URL = 'https://accounts.spotify.com/api/token'
const SCOPES = "playlist-read-private playlist-read-collaborative user-library-read user-follow-read user-read-private";
const AUTH_REDIRECT_URL = "http://localhost:5173/"
const CODE_VERIFIER_LENGTH = 50
const STATE_LENGTH = 50
const EXPIRATION_BUFFER = 5 * 60 * 1000


/**
 * Redirects to the spotify authorization endpoint
 */
export async function authorizeUrl() {
  // Safe codeVerifier and state to localStorage (authDate Store)
  // This is needed, cause the values for the token request have to be the same
  const codeVerifier = generateCodeVerifier(CODE_VERIFIER_LENGTH);
  const state = Base64.encodeURI(generateCodeVerifier(STATE_LENGTH));
  clearAuthData();
  setCodeVerifier(codeVerifier);
  setState(state);

  const params = new URLSearchParams({
    response_type: 'code',
    client_id: PUBLIC_SPOTIFY_ID,
    redirect_uri: AUTH_REDIRECT_URL,
    code_challenge_method: 'S256',
    code_challenge: await createCodeChallenge(codeVerifier),
    state: state,
    show_dialog: "true",
    scope: SCOPES,    
  });


  return `${AUTHORIZE_URL}?${params}`;
}

/**
 * Tries to authenticate the user.
 * Redirects Home, when the user cannot be authenticated, or when a new authentication was made with spotify.
 * 
 * @param {string | null} code When give, requests a access token from spotify
 * @returns Wheather the authentication was successful or not
 */
export async function authenticate(code: string | null = null) {
  const data = get(authData);

  if (code && data.codeVerifier) {
    // Login was already made and the code is available.
    await tokenRequest(code, data.codeVerifier);
  }

  if (!isAuthenticated()) {
    // If the authData is invalid, clear it and return Home for a fresh start.
    clearAuthData();
    throw error(401, {
      message: 'Authentication Invalid.'
    });
  }

  if ((Date.now() + EXPIRATION_BUFFER) > Date.parse(data.tokenExpires!)) {
    
    await refreshToken();
  }

  // Already Authorized
}


/**
 * Validates the authData.
 * Does not check, weather or not the token and refresh_token are valid for spotify.
 * @returns {boolean} true when authData is valid, false otherwise.
 */
export function isAuthenticated(): boolean {
  const data = get(authData);
  
  if (!data.codeVerifier) return false
  if (!data.state) return false
  if (!data.token) return false
  if (!data.refresh_token) return false
  if (!data.tokenExpires) return false
  if (!data.tokenScope) return false

  if (data.codeVerifier.length != CODE_VERIFIER_LENGTH) return false
  if (Base64.decode(data.state).length != STATE_LENGTH) return false
  if (data.tokenScope != SCOPES) return false
  
  return true;
}


/**
 * Retrieves the access_token and refresh_token from spotify using the given code.
 * 
 * @param {string | null} code The code from the initial request
 * @param {string | undefined} codeVerifier The codeVerifier of the uer
 * @throws {Error} When any error occures
 */
async function tokenRequest(code: string, codeVerifier: string) {


  const response = await fetch(TOKEN_API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: new URLSearchParams({
      grant_type: 'authorization_code',
      client_id: PUBLIC_SPOTIFY_ID,
      redirect_uri: AUTH_REDIRECT_URL,
      code_verifier: codeVerifier,
      code: code
    }),
  });

  if (!response.ok) {
    // Clear authData to allow for a fresh login.
    clearAuthData();
    throw error(500, {
      message: 'Error retrieving the access token from spotify.'
    });
  }

  const json: TokenApiResult = await response.json();

  setToken(json.access_token);
  setTokenScope(json.scope);
  setRefreshToken(json.refresh_token);
  setTokenExpires(new Date(Date.now() + (Number(json.expires_in) * 1000)).toISOString());
}



async function refreshToken() {

  const response = await fetch(TOKEN_API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: new URLSearchParams({
      grant_type: 'refresh_token',
      client_id: PUBLIC_SPOTIFY_ID,
      refresh_token: get(authData).refresh_token!,
    }),
  });

  if (!response.ok) {    
    // Clear authData to allow for a fresh login.
    clearAuthData();
    throw error(500, {
      message: 'Could not refresh access token.'
    });
  }

  const json: TokenApiResult = await response.json();

  setToken(json.access_token);
  setTokenScope(json.scope);
  setTokenExpires(new Date(Date.now() + (Number(json.expires_in) * 1000)).toISOString());
}