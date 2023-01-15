<script>
	import { authorizeUrl } from '$lib/auth/authorize';
	import { page } from '$app/stores';
	import { authenticate } from '$lib/auth/authorize';
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import { state } from '$lib/stores/state';

	if (browser) {
		getToken();
	}

	async function getToken() {
		const code = $page.url.searchParams.get('code');
		await authenticate(code);
		await goto('/');
	}

	async function authorize() {
		await goto(await authorizeUrl());
	}
</script>

<h1>Welcome to Expandify</h1>

{#if !$state.authenticated}
	<button on:click={authorize}>Login with Spotify</button>
{/if}

<style lang="scss">
</style>
