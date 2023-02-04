<script lang="ts">
	import { afterNavigate, goto } from '$app/navigation';
	import NavBar from '$lib/components/navbar/NavBar.svelte';
	import { hasSpotifyAccess } from '$lib/stores/spotify-access';

	import type { AfterNavigate } from '@sveltejs/kit';


	// After each navigation, ensure that the user is authenticated.
	// This will NOT cause an infinite loop, since "/" does not use this layout.
	// The callback will be destroyed, when this layout is not in use anymore.
	afterNavigate((_: AfterNavigate) => {
		if (!hasSpotifyAccess) { 
			goto("/"); 
		};
	})

</script>


<div class="page">
{#if hasSpotifyAccess}		
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
			width: 100%;
			box-sizing: border-box;
			padding: 2rem 2rem 2rem 2rem;
			width: 100%;						
		}
	}
</style>
