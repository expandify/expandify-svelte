<script lang="js">
    import { browser } from "$app/environment";
    import WebPlayer from "$lib/components/WebPlayer.svelte";
    import { Spotify } from "$lib/data/spotify";
	import { dependencies } from "$lib/stores/dependencies";
    import { spotifySession } from "$lib/stores/spotifySession";



  //dependencies.setDependencies(true, true, true, true, true);
  dependencies.noNeeds();

  
</script>

<h1>Dashboard</h1>

<div class="web-player">
  <WebPlayer></WebPlayer>
</div>




<style lang="scss">
  .web-player {
    height: 15rem;
  }
</style>