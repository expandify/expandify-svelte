<script lang="ts">
	import type { Artist } from "$lib/types/spotify";
	import ArtistImage from "../images/ArtistImage.svelte";
	import Card from "./Card.svelte";

  export let artist: Artist;  
  
</script>

<Card title={artist.name} 
      subtitle={"Artist"}
      href={null}>

      <ArtistImage artist={artist}></ArtistImage>
</Card>

<style lang="scss">

</style>