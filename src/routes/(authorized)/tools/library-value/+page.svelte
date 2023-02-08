<script lang="ts">
	import ButtonSimple from "$lib/components/buttons/ButtonSimple.svelte";
	import ButtonSimpleElevated from "$lib/components/buttons/ButtonSimpleElevated.svelte";
import ButtonSpotify from "$lib/components/buttons/ButtonSpotify.svelte";
	import LoadingDots from "$lib/components/loading/LoadingDots.svelte";
	import { savedTrack } from "$lib/spotify/converter";
	import { calculateLibraryValue } from "./value-calculator";
	//import { calculateLibraryValue } from "./value-calculator";

	let savedAlbums = false;
	let ownPlaylists = false;
	let followedPlaylists = false;
	let savedTracks = false;

	let calculating = false;
	let libraryValue: number | null = null;

	async function calcLibrary() {
		calculating = true;
		libraryValue = await calculateLibraryValue(savedAlbums, ownPlaylists, followedPlaylists, savedTracks);
		calculating = false;
	}
  
</script>

<span>Select the library parts you want to calculate.</span>
<div class="selection">
<label>
	<input type=checkbox bind:checked={savedAlbums}>
	<span>Saved Albums</span>
</label>
<label>
	<input type=checkbox bind:checked={ownPlaylists} >
	<span>Created Playlists</span>
</label>
<label>
	<input type=checkbox bind:checked={followedPlaylists} >
	<span>Followed Playlists</span>
</label>
<label>
	<input type=checkbox bind:checked={savedTracks} >
	<span>Saved Tracks</span>
</label>
</div>

{#if savedAlbums || ownPlaylists || followedPlaylists || savedTracks}
	<ButtonSimpleElevated on:click={calcLibrary}><h2>Calculate Library Value</h2></ButtonSimpleElevated>	
{/if}


{#if calculating}
	<LoadingDots message={"Calculating"} />
{:else if libraryValue}
	<h2>Library value is {libraryValue}$</h2>
{/if}



<style lang="scss">
	.selection {
		display: flex;
		flex-direction: column;
	}
</style>
