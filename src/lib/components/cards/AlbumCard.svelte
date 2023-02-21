<script lang="ts">
	import type { Album } from "$lib/types/spotify";
	import AlbumImage from "../images/AlbumImage.svelte";
	import Card from "./Card.svelte";

  export let album: Album;  
  
</script>

<Card title={album.name} 
      subtitle={album.artists.map(a => a.name).join(", ")}
      href={`/information/album/${album.id}`}>

      <AlbumImage album={album}></AlbumImage>
</Card>

<style lang="scss">

</style>