<script lang="ts">
	import { goto } from '$app/navigation';
	import NavBar from '$lib/components/navbar/NavBar.svelte';
	import { session } from '$lib/stores/session';
	
	
	$: if (!($session)) goto("/");

</script>


<div class="page">
{#if $session}		
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
