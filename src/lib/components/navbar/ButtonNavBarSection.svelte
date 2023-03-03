<script lang="ts">
	import { goto } from "$app/navigation";
	import { logout } from "$lib/auth/auth";
	import { Spotify } from "$lib/data/spotify";
	import Button from "../common/Button.svelte";


	function logouts() {
		logout();
    goto("/");
	} 
</script>
  <section class="section">
		<Button on:click={Spotify.loadLibraryToStores} text="Reload" style="spotify"></Button>
		<Button on:click={logouts} text="Logout" style="base"></Button>
  </section>


<style lang="scss">
	
	.section {
    display: flex;
    flex-direction: column;
		gap: 0.5rem;

		h5 {
			margin: 1rem 0;
			padding: 0;
		}
	}

</style>
