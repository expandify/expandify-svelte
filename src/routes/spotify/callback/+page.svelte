<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';	
	import { PUBLIC_SPOTIFY_ID, PUBLIC_SPOTIFY_REDIRECT_URI } from '$env/static/public';
	import { Spotify, spotifyData } from '$lib/stores/spotify';
	import { browser } from '$app/environment';

	const code = $page.url.searchParams.get('code');

	if (browser) {
		
		if (!code || !$spotifyData.codeVerifier) {
			// TODO error handling
			goto("/");
		}

		tokenRequest().then(() => {
			goto("/dashboard");
		})
	}

	

	async function tokenRequest() {
		const response = await fetch(Spotify.TOKEN_API_URL, {
			method: 'POST',
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			body: new URLSearchParams({
				grant_type: 'authorization_code',
				client_id: PUBLIC_SPOTIFY_ID,
				redirect_uri: PUBLIC_SPOTIFY_REDIRECT_URI,
				code_verifier: $spotifyData.codeVerifier!,
				code: code!
			})
		});

		if (!response.ok) {
			// TODO error handling
			goto("/");
		}

		const json: TokenApiResult = await response.json();

		$spotifyData.token = json.access_token;
		$spotifyData.tokenScope = json.scope;
		$spotifyData.refresh_token = json.refresh_token;
		$spotifyData.tokenExpires = new Date(Date.now() + Number(json.expires_in) * 1000).toISOString();
	}
</script>