import { Spotify } from '$lib/stores/spotify';
import SpotifyWebApi from 'spotify-web-api-js';
import { get } from 'svelte/store';
import { PUBLIC_SPOTIFY_ID } from '$env/static/public';

export async function makeRequest<T>(func: (api: SpotifyWebApi.SpotifyWebApiJs) => Promise<T>): Promise<T> {
  let api = new SpotifyWebApi();
  try {   

    api.setAccessToken(get(Spotify.data).token!);    
    const data = await func(api);
    return data;

  } catch (error: any) {

    if (error.status !== 401 && error.status !== 429) {
      return Promise.reject({status: error.status, message: "Error making a request."})
    }

    if (error.status === 401) {
      await refreshToken();
      api.setAccessToken(get(Spotify.data).token!);      
    }

    if (error.status === 429) {
      const retry = error.getResponseHeader('Retry-After');
      await new Promise((r) => setTimeout(r, Number(retry) * 1000))
    } 
    
    return await func(api);
    
  }
}


async function refreshToken() {
	
	const response = await fetch(Spotify.TOKEN_API_URL, {
		method: 'POST',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: new URLSearchParams({
			grant_type: 'refresh_token',
			client_id: PUBLIC_SPOTIFY_ID,
			refresh_token: get(Spotify.data).refresh_token!
		}).toString()
	});

	if (!response.ok) {
		return Promise.reject({status: 401, message: "Could not refresh access token"});
	}

	const json: TokenApiResult = await response.json();
	
	Spotify.data.update((value) => ({
		...value,
		token: json.access_token,
		tokenScope: json.scope,
		tokenExpires: new Date(Date.now() + Number(json.expires_in) * 1000).toISOString()
	}));
	
	return Promise.resolve();
}
