<script lang="ts">
	import type { PlaylistSimplified } from "$lib/types/spotify";
	import PlaylistImage from "../images/PlaylistImage.svelte";
	import Card from "./Card.svelte";

  export let playlist: PlaylistSimplified;  
  
</script>

<Card title={playlist.name} 
      subtitle={`By ${playlist.owner.display_name}`}
      href={null}>

      <PlaylistImage playlist={playlist}></PlaylistImage>
</Card>

<style lang="scss">

</style>