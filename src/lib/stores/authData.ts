import { writable } from 'svelte/store'
import { browser } from '$app/environment';

interface AuthData {
  codeVerifier?: string;
  state?: string;
  token?: string;
  refresh_token?: string;
  tokenExpires?: Date;
};

export const authData = writable<AuthData>({});


if (browser){
  authData.set(localStorage.authData || {});
  authData.subscribe((value) => localStorage.authData = value);
}




