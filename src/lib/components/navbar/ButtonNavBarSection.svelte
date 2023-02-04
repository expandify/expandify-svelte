<script lang="ts">
	import { goto } from "$app/navigation";
	import ButtonSimple from "$lib/components/buttons/ButtonSimple.svelte";
	import ButtonSpotify from "$lib/components/buttons/ButtonSpotify.svelte";
	import { reloadLibrary } from "$lib/spotify/api/library";
	import { removeAccess } from "$lib/stores/spotify-access";


	function logout() {
		removeAccess();
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
    flex-direction: column;

		h5 {
			margin: 1rem 0;
			padding: 0;
		}
	}

</style>
