import { spotifyAccessData } from '$lib/stores/spotify-access';
import SpotifyWebApi from 'spotify-web-api-js';
import { get } from 'svelte/store';
import { PUBLIC_SPOTIFY_ID, PUBLIC_SPOTIFY_REDIRECT_URI } from '$env/static/public';
import { TOKEN_API_URL } from './constants';

declare interface TokenApiResult {
	access_token: string;
	scope: string;
	expires_in: number;
	refresh_token: string;
}

export async function makeRequest<T>(func: (api: SpotifyWebApi.SpotifyWebApiJs) => Promise<T>): Promise<T> {
  let api = new SpotifyWebApi();
  try {   		

    api.setAccessToken(get(spotifyAccessData).token!);    
    const data = await func(api);
    return data;

  } catch (error: any) {

    if (error.status !== 401 && error.status !== 429) {
      return Promise.reject({status: error.status, message: "Error making a request."})
    }

    if (error.status === 401) {
      await refreshToken();
      api.setAccessToken(get(spotifyAccessData).token!);      
    }

    if (error.status === 429) {
      const retry = error.getResponseHeader('Retry-After');
      await new Promise((r) => setTimeout(r, Number(retry) * 1000))
    } 
    
    return await func(api);
    
  }
}


export async function refreshToken() {
	const nowPlus10Sec = new Date(Date.now() + (10 * 1000)).toISOString();

	if (get(spotifyAccessData).tokenExpires! > nowPlus10Sec) {
		return;
	}

	const response = await fetch(TOKEN_API_URL, {
		method: 'POST',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: new URLSearchParams({
			grant_type: 'refresh_token',
			client_id: PUBLIC_SPOTIFY_ID,
			refresh_token: get(spotifyAccessData).refresh_token!
		}).toString()
	});

	console.log(response);
	
	if (!response.ok) {
		return Promise.reject({status: 401, message: "Could not refresh access token"});
	}

	const json: TokenApiResult = await response.json();
	
	spotifyAccessData.update((value) => ({
		...value,
		token: json.access_token,
		tokenScope: json.scope,
		tokenExpires: new Date(Date.now() + Number(json.expires_in) * 1000).toISOString()
	}));
	
	return Promise.resolve();
}

export async function tokenRequest(code: string) {
	const response = await fetch(TOKEN_API_URL, {
		method: 'POST',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: new URLSearchParams({
			grant_type: 'authorization_code',
			client_id: PUBLIC_SPOTIFY_ID,
			redirect_uri: PUBLIC_SPOTIFY_REDIRECT_URI,
			code_verifier: get(spotifyAccessData).codeVerifier!,
			code: code!
		})
	});

	if (!response.ok) {
		return Promise.reject({status: 401, message: "Could not get access token"});
	}

	const json: TokenApiResult = await response.json();

	spotifyAccessData.update((value) => ({
		...value,
		token: json.access_token,
		tokenScope: json.scope,
		refresh_token: json.refresh_token,
		tokenExpires: new Date(Date.now() + Number(json.expires_in) * 1000).toISOString()
	}));
}