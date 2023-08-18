import {Base64} from "js-base64";
import {createCodeChallenge, generateRandomString} from "./crypto";
import {PUBLIC_SPOTIFY_ID, PUBLIC_SPOTIFY_REDIRECT_URI} from '$env/static/public';
import {spotifySession} from "$lib/stores/spotifySession";
import {get} from "svelte/store";

const SCOPES = 'playlist-read-private playlist-read-collaborative user-library-read user-follow-read user-read-private app-remote-control streaming user-read-playback-state user-modify-playback-state user-read-currently-playing user-read-email';
const CODE_VERIFIER_LENGTH = 50;
const STATE_LENGTH = 50;
const TOKEN_API_URL = 'https://accounts.spotify.com/api/token';
const AUTHORIZE_URL = 'https://accounts.spotify.com/authorize';
const CODE_VERIFIER_LOCAL_STORAGE = "code-verifier";
const STATE_LOCAL_STORAGE = "state"


/**
 *
 * @returns
 */
export async function authorizeUrl() {
    const codeVerifier = generateRandomString(CODE_VERIFIER_LENGTH);
    const state = Base64.encodeURI(generateRandomString(STATE_LENGTH));
    localStorage.setItem(CODE_VERIFIER_LOCAL_STORAGE, codeVerifier);
    localStorage.setItem(STATE_LOCAL_STORAGE, state);

    const params = new URLSearchParams({
        response_type: 'code',
        client_id: PUBLIC_SPOTIFY_ID,
        redirect_uri: PUBLIC_SPOTIFY_REDIRECT_URI,
        code_challenge_method: 'S256',
        code_challenge: await createCodeChallenge(codeVerifier),
        state: state,
        show_dialog: 'true',
        scope: SCOPES
    });

    return `${AUTHORIZE_URL}?${params}`;
}

/**
 *
 * @param code
 * @param state
 * @returns
 */
export async function createUserSession(code: string, state: string) {
    const codeVerifier = localStorage.getItem(CODE_VERIFIER_LOCAL_STORAGE);
    const lsState = localStorage.getItem(STATE_LOCAL_STORAGE);


    if (!lsState || !codeVerifier || state !== lsState) {

        spotifySession.set(null);
        return Promise.resolve();
    }

    const response = await fetch(TOKEN_API_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: new URLSearchParams({
            grant_type: 'authorization_code',
            client_id: PUBLIC_SPOTIFY_ID,
            redirect_uri: PUBLIC_SPOTIFY_REDIRECT_URI,
            code_verifier: codeVerifier!,
            code: code
        })
    });

    if (!response.ok) {
        spotifySession.set(null);
        return Promise.reject();
    }

    type TokenApiResult = { access_token: string, scope: string, expires_in: number, refresh_token: string }
    const json: TokenApiResult = await response.json();

    spotifySession.set({
        token: json.access_token,
        tokenScope: json.scope,
        refreshToken: json.refresh_token,
        expirationDate: expiresInToDate(Number(json.expires_in))
    });

    return Promise.resolve()
}

/**
 *
 */
export function logout() {
    spotifySession.set(null);
}

/**
 *
 */
export function startAutoRefresh() {
    spotifySession.subscribe(s => {
        if (!s) {
            return;
        }

        const fiveMinInMs = 5 * 60 * 1000;
        // Sets refresh timeout to 10sec for testing
        //const fiveMinInMs = (((59 * 60) + 50) * 1000);
        const msToExpire = new Date(s.expirationDate).getTime() - Date.now();
        const msOneMinBeforeExpire = msToExpire - fiveMinInMs;
        const msTimer = Math.max(0, msOneMinBeforeExpire);

        setTimeout(() => refreshUserSession(), msTimer);
    });
}


async function refreshUserSession() {
    const refreshToken = get(spotifySession)?.refreshToken;


    if (!refreshToken) {
        spotifySession.set(null);
        return;
    }

    const response = await fetch(TOKEN_API_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: new URLSearchParams({
            grant_type: 'refresh_token',
            client_id: PUBLIC_SPOTIFY_ID,
            refresh_token: refreshToken
        }).toString()
    });

    if (!response.ok) {
        spotifySession.set(null);
        return;
    }

    type TokenApiResult = { access_token: string, scope: string, expires_in: number, refresh_token: string }
    const json: TokenApiResult = await response.json();


    spotifySession.set({
        token: json.access_token,
        tokenScope: json.scope,
        refreshToken: json.refresh_token,
        expirationDate: expiresInToDate(Number(json.expires_in))
    });

    return Promise.resolve();
}


function expiresInToDate(expiresInSec: number) {

    return new Date(Date.now() + (expiresInSec * 1000));
}