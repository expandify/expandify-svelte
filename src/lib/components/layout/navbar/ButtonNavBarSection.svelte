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
    <ButtonSpotify on:click={reloadLibrary}><h5>Reload</h5></ButtonSpotify>
    <ButtonSimple on:click={logout}><h5>Logout</h5></ButtonSimple>
  </section>


<style lang="scss">
	
	#section {
    display: flex;
    flex-direction: row;

		h5 {
			margin: 1rem 0;
			padding: 0;
		}
	}

	@media screen and (max-height: 500px) {
	
}
</style>
