<script lang="ts">
	import { state } from "$lib/stores/state";
  import { reloadSavedAlbumsWithTracks } from '$lib/spotify/album';
	import { reloadFollowedArtists } from '$lib/spotify/artist';
	import { reloadUserPlaylistsWithTracks } from '$lib/spotify/playlist';
	import { reloadSavedTracks } from "$lib/spotify/track";
	import { clearAuthData } from "$lib/stores/authData";
	import { Cache } from "$lib/stores/cache";
	import { goto } from "$app/navigation";
	import ButtonSimple from "$lib/components/buttons/ButtonSimple.svelte";
	import ButtonSpotify from "$lib/components/buttons/ButtonSpotify.svelte";

  function reloadLibrary() {
		reloadUserPlaylistsWithTracks();
		reloadSavedAlbumsWithTracks();
    reloadSavedTracks();
		reloadFollowedArtists();
	}

	function logout() {
		clearAuthData();
		Cache.clearAll();
    goto("/");
	} 
</script>
  <section id=section>
    <ButtonSpotify on:click={reloadLibrary}><h4>Reload Library</h4></ButtonSpotify>
    {#if $state.authenticated}
    <ButtonSimple on:click={logout}><h4>Logout</h4></ButtonSimple>
    {/if}	
  </section>


<style lang="scss">
	
	#section {
    display: flex;
    flex-direction: column;     
  }
</style>
