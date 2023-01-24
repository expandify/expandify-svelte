<script lang="ts">
	import { goto } from '$app/navigation';
	import { Spotify, spotifyData} from '$lib/stores/spotify';
	import { Base64 } from 'js-base64';
	import { PUBLIC_SPOTIFY_ID, PUBLIC_SPOTIFY_REDIRECT_URI } from '$env/static/public';
	import { createCodeChallenge, generateCodeVerifier } from './crypto';
	import { browser } from '$app/environment';

	async function authorizeUrl() {
		const codeVerifier = generateCodeVerifier(Spotify.CODE_VERIFIER_LENGTH);
		const state = Base64.encodeURI(generateCodeVerifier(Spotify.STATE_LENGTH));
		$spotifyData.codeVerifier = codeVerifier;
		$spotifyData.state = state;

		const params = new URLSearchParams({
			response_type: 'code',
			client_id: PUBLIC_SPOTIFY_ID,
			redirect_uri: PUBLIC_SPOTIFY_REDIRECT_URI,
			code_challenge_method: 'S256',
			code_challenge: await createCodeChallenge(codeVerifier),
			state: state,
			show_dialog: 'true',
			scope: Spotify.SCOPES
		});

		return `${Spotify.AUTHORIZE_URL}?${params}`;
	}

	if (browser) {
		// GOTO spotify authorization endpoint
		authorizeUrl().then((url) => {
			goto(url);
		});
	}	
</script>