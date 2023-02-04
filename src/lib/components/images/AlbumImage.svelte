<script lang="ts">
	import Image from "./Image.svelte";

  export let album: AlbumSimplified;
  export let borderRadius = "1rem";

</script>

<Image image={album.images.at(0)?.url} 
       imageAlt={album.name} 
       borderRadius={borderRadius} 
       fallbackSvg="album"/>

<style lang="scss">

</style>