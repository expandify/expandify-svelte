<script lang="js">
    import WebPlayer from "$lib/components/WebPlayer.svelte";
	import { dependencies } from "$lib/stores/dependencies";


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