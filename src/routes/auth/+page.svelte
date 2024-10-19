<script lang="ts">
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import { authorizeUrl, createUserSession } from '$lib/auth/auth';
	import { page } from '$app/stores';

	if (browser) {
		login();
	}

	async function login() {
		const code = $page.url.searchParams.get('code');
		const state = $page.url.searchParams.get('state');


		if (!code || !state) {
			window.location.href = await authorizeUrl();
			return
		}

		try {
			await createUserSession(code!, state!);
			await goto('/dashboard');
		} catch (_) {
			await goto('/');
		}
	}

</script>
