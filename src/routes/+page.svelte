<script lang="ts">
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import Button from '$lib/components/common/Button.svelte';
	import { spotifySession } from '$lib/stores/spotifySession';


	if (browser && $spotifySession) { 
		goto("/dashboard"); 
	}
</script>

<main class="main">
	<h1>Welcome to Expandify</h1>
	<Button on:click={() => goto("/auth")} text="Login With Spotify" style="spotify"></Button>
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
