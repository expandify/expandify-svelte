<script lang="ts">
	import type { AlbumSimplified } from "$lib/types/spotify";
	import Image from "./Image.svelte";

  export let album: AlbumSimplified | null | undefined;
  export let borderRadius = "1rem";

</script>

<Image image={album?.images?.at(0)?.url} 
       imageAlt={album?.name} 
       borderRadius={borderRadius} 
       fallbackSvg="album"/>

<style lang="scss">

</style>