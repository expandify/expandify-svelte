<script lang="ts">
	import { goto } from '$app/navigation';
	import { spotifyAccessData } from '$lib/stores/spotify-access';
	import { Base64 } from 'js-base64';
	import { PUBLIC_SPOTIFY_ID, PUBLIC_SPOTIFY_REDIRECT_URI } from '$env/static/public';
	import { createCodeChallenge, generateCodeVerifier } from './crypto';
	import { browser } from '$app/environment';
	import { AUTHORIZE_URL, CODE_VERIFIER_LENGTH, SCOPES, STATE_LENGTH } from '$lib/spotify/constants';

	async function authorizeUrl() {
		const codeVerifier = generateCodeVerifier(CODE_VERIFIER_LENGTH);
		const state = Base64.encodeURI(generateCodeVerifier(STATE_LENGTH));
		$spotifyAccessData.codeVerifier = codeVerifier;
		$spotifyAccessData.state = state;

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

	if (browser) {
		// GOTO spotify authorization endpoint
		authorizeUrl().then((url) => {
			goto(url);
		});
	}	
</script>