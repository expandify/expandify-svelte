<script lang="ts">
	  import { reloadSavedAlbumsWithTracks } from '$lib/spotify/album';
	import { reloadFollowedArtists } from '$lib/spotify/artist';
	import { reloadUserPlaylistsWithTracks } from '$lib/spotify/playlist';
	import { reloadSavedTracks } from "$lib/spotify/track";
	import { Cache } from "$lib/stores/cache";
	import { goto } from "$app/navigation";
	import ButtonSimple from "$lib/components/buttons/ButtonSimple.svelte";
	import ButtonSpotify from "$lib/components/buttons/ButtonSpotify.svelte";
	import { Spotify } from '$lib/stores/spotify';

  function reloadLibrary() {
		reloadUserPlaylistsWithTracks();
		reloadSavedAlbumsWithTracks();
    reloadSavedTracks();
		reloadFollowedArtists();
	}

	function logout() {
		Spotify.logout();
		Cache.clearAll();
    goto("/");
	} 
</script>
  <section id=section>
    <ButtonSpotify on:click={reloadLibrary}><h4>Reload Library</h4></ButtonSpotify>
    <ButtonSimple on:click={logout}><h4>Logout</h4></ButtonSimple>
  </section>


<style lang="scss">
	
	#section {
    display: flex;
    flex-direction: column;     
  }
</style>
