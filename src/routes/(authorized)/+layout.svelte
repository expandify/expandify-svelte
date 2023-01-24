<script lang="ts">
	import { afterNavigate, goto } from '$app/navigation';
	import NavBar from '$lib/components/layout/navbar/NavBar.svelte';
	import { Spotify } from '$lib/stores/spotify';

	import type { AfterNavigate } from '@sveltejs/kit';


	// After each navigation, ensure that the user is authenticated.
	// This will NOT cause an infinite loop, since "/" does not use this layout.
	// The callback will be destroyed, when this layout is not in use anymore.
	afterNavigate((_: AfterNavigate) => {
		if (!Spotify.isLoggedIn) { 
			goto("/"); 
		};
	})

</script>


<div class="page">
{#if Spotify.isLoggedIn}		
	<NavBar />
	<main class="content"><slot /></main>
{/if}
</div>

<style lang="scss">
	.page {
		display: flex;
		flex-direction: row;
		min-height: 100vh;					
		.content {									
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			box-sizing: border-box;
			padding: 2rem 2rem 2rem 1rem;
			width: 100%;			
			
		}
	}
</style>
