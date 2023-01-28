<script lang="ts">
	import { goto } from "$app/navigation";
	import ButtonSimple from "$lib/components/buttons/ButtonSimple.svelte";
	import ButtonSpotify from "$lib/components/buttons/ButtonSpotify.svelte";
	import { Albums } from "$lib/stores/library/albums";
	import { Artists } from "$lib/stores/library/artists";
	import { Playlists } from "$lib/stores/library/playlists";
	import { Tracks } from "$lib/stores/library/tracks";
	import { Spotify } from '$lib/stores/spotify';

  function reloadLibrary() {
		Playlists.loadAll();
		Albums.loadAll();
		Artists.loadAll();
		Tracks.loadAll();
	}

	function logout() {
		Spotify.logout();
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
