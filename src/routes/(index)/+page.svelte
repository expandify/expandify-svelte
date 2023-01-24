<script>
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import ButtonSpotify from '$lib/components/buttons/ButtonSpotify.svelte';
	import { Spotify } from '$lib/stores/spotify';

	if (browser && Spotify.isLoggedIn) { 
		goto("/dashboard"); 
	};
</script>

<main class="main">
	<h1>Welcome to Expandify</h1>
	<ButtonSpotify on:click={() => goto("/spotify/auth")}><h3>Login with Spotify</h3></ButtonSpotify>
</main>




<style lang="scss">
	.main {
		min-height: 100vh;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
</style>
