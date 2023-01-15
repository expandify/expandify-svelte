<script lang="ts">
	import '@picocss/pico/css/pico.css';
	import '../app.css';
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import NavBar from '$lib/components/NavBar.svelte';
	import { state } from '$lib/stores/state';
	import LoadingIndicator from '$lib/components/LoadingIndicator.svelte';
	import { albumCache, artistCache, Cache, playlistCache, trackCache } from '$lib/stores/cache';
  import { SunIcon, MoonIcon } from 'svelte-feather-icons';

	if (browser && !$state.authenticated) {
		goto('/');
	}
	let indicators: {
		[id: string]: { max?: number | null; value?: number | null; msg: string; error: boolean };
	} = {};

	function createIndicator<T extends SpotifyData>(
		cache: Cache.Data<T>,
		msg: string,
		msgSecondary: string
	) {
		let message = '';
		let error = false;

		switch (cache.state) {
			case Cache.State.Error:
				message = `Error Loading ${msg}`;
				error = true;
				break;

			case Cache.State.Loading:
				message = msg;
				break;

			case Cache.State.LoadingSecondary:
				message = msgSecondary;
				break;

			default:
				delete indicators[cache.name];
				// This makes sure svelte notices the update on the indicators
				indicators = indicators;
				return;
		}

		indicators[cache.name] = {
			msg: message,
			max: cache.totalItems,
			value: cache.loadedItems,
			error: error
		};
	}

	// This calls the functions everytime a store changes.
	$: createIndicator($albumCache, 'Albums', 'Songs Of Albums');
	$: createIndicator($artistCache, 'Artists', 'Artists');
	$: createIndicator($playlistCache, 'Playlists', 'Songs Of Playlists');
	$: createIndicator($trackCache, 'Tracks', 'Tracks');


  function toggleTheme() {
    if ($state.darkTheme) {
      document.documentElement.setAttribute("data-theme", "light");
      return;
    }
    document.documentElement.setAttribute("data-theme", "dark");
            
  }
</script>

<div class="page">
	{#if $state.authenticated}
		<div class="navbar">
			<NavBar />
		</div>
	{/if}
	<div class="content">
		<slot />
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <div class="theme-toggle" on:click={toggleTheme}>
      {#if $state.darkTheme}
        <SunIcon size="32"></SunIcon>  
      {:else}
        <MoonIcon size="32"></MoonIcon>
      {/if}
    </div>
		<div class="floating-indicators">
			{#each Object.values(indicators) as indicator}
				<LoadingIndicator
					max={indicator.max}
					value={indicator.value}
					message={indicator.msg}
					error={indicator.error}
				/>
			{/each}
		</div>    
	</div>
</div>

<style lang="scss">
	.page {
		position: relative;
		display: flex;
		flex-direction: row;
		justify-content: space-between;

		.navbar {
			position: -webkit-sticky;
			position: sticky;
			top: 0rem;
			bottom: 0;
			width: 20rem;
			height: 100vh;
		}

		.content {
			padding: 2rem 2rem 2rem 1rem;
			width: 100%;

      .theme-toggle {
        width: fit-content;
        height: fit-content;
        padding: 0.7rem;
        border-radius: 100%;
        background-color: var(--h3-color);
        color: var(--primary);
        position: fixed;
        bottom: 1rem;
        right: 1rem;
      }

      .theme-toggle:hover {
        background-color: var(--h6-color);
        color: var(--primary-hover);
        cursor: pointer;
      }

			.floating-indicators {
				width: 17rem;
				position: fixed;
				bottom: 4rem;
				right: 0rem;
			}
		}
	}
</style>
