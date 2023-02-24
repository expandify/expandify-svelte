import SpotifyWebApi from 'spotify-web-api-js';
import {get} from 'svelte/store';
import {spotifySession} from '$lib/stores/spotifySession';

export async function makeRequest<T>(func: (api: SpotifyWebApi.SpotifyWebApiJs) => Promise<T>): Promise<T> {
  let api = new SpotifyWebApi();
  try {   		

    api.setAccessToken(get(spotifySession)?.token!);

    return await func(api);

  } catch (error: any) {

    if (error.status !== 429) {
			return Promise.reject({status: error.status, message: "Error making a request."})
    } 

		const retry = error.getResponseHeader('Retry-After');
		await new Promise((r) => setTimeout(r, Number(retry) * 1000))
		return await func(api);		    
  }
}

