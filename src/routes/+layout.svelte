<script lang="ts">
	import '../app.scss';
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import NavBar from '$lib/components/layout/navbar/NavBar.svelte';
	import { state } from '$lib/stores/state';
	import FloatingCacheIndicators from '$lib/components/layout/FloatingCacheIndicators.svelte';
	import { navigating } from '$app/stores';

	// Whenever there is a navigation, check if we are authorized.
	// If not goto Home.
	$: $navigating, (() => {
		if (browser && 
				$navigating && 
				$navigating.to?.url.pathname !== "/" && 
				!$state.authenticated
			) { goto('/'); };
	})();
</script>

<div class="page">
	{#if $state.authenticated}		
		<NavBar />
	{/if}
	<main class="content"><slot /></main>
	<FloatingCacheIndicators></FloatingCacheIndicators>
</div>

<style lang="scss">
	.page {
		position: relative;
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
