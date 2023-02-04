<script lang="ts">
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';	
	import { spotifyAccessData } from '$lib/stores/spotify-access';
	import { browser } from '$app/environment';
	import { tokenRequest } from '$lib/spotify/request';
	import { laodUser } from '$lib/spotify/api/user';
	
	const code = $page.url.searchParams.get('code');

	if (browser) {
		
		if (!code || !$spotifyAccessData.codeVerifier) {
			// TODO error handling
			goto("/");
		}

		tokenRequest(code!).catch(() => goto("/"))
		laodUser();
		goto("/dashboard");
	}

	
</script>