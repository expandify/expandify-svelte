<script lang="ts">
	import { Cache } from "$lib/stores/cache";
	import { onDestroy } from "svelte";
	import type { Writable } from "svelte/store";
	import AnnouncementIndicator from "../AnnouncementIndicator.svelte";
	import ErrorIndicator from "../ErrorIndicator.svelte";
	import LoadingIndicator from "../LoadingIndicator.svelte";
	import SuccessIndicator from "../SuccessIndicator.svelte";

	export let cache: Writable<Cache.Data<SpotifyData>>;
	export let finishedMessage: string | null = null;
	export let errorMessage: string | null = null;
	export let loadingMessage: string | null = null;
	export let loadingSecondaryMessage: string | null = null;
	export let emptyMessage: string | null = null;
	
	const finishedTimeout = 3000;
	const errorTimeout = 8000;
	const announcementTimeout = 5000;
	
	let showIndicator = true;
	
	const unsubscriber = cache.subscribe((value) => {
		showIndicator = true;
		switch (value.state) {
			case Cache.State.Finished:
				setTimeout(() => showIndicator = false, finishedTimeout);
				break;
			case Cache.State.Error:
				setTimeout(() => showIndicator = false, errorTimeout);
				break;
			case Cache.State.Empty:
				setTimeout(() => showIndicator = false, announcementTimeout);
				break;		
			default:
				break;
		}		
	})
	onDestroy(() => unsubscriber())

</script>

{#if showIndicator && finishedMessage && $cache.state == Cache.State.Finished}
<SuccessIndicator message={finishedMessage}/>

{:else if showIndicator && errorMessage && $cache.state == Cache.State.Error}
<ErrorIndicator message={errorMessage}/>

{:else if loadingMessage && $cache.state == Cache.State.Loading}
<LoadingIndicator
  max={$cache.totalItems}
  value={$cache.loadedItems}
  message={loadingMessage}/>

{:else if loadingSecondaryMessage && $cache.state == Cache.State.LoadingSecondary}
<LoadingIndicator
  max={$cache.totalItems}
  value={$cache.loadedItems}
  message={loadingSecondaryMessage}/>

{:else if showIndicator && emptyMessage && $cache.state == Cache.State.Empty}
<AnnouncementIndicator message={emptyMessage}/>

{/if}