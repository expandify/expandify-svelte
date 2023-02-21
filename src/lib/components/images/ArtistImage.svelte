<script lang="ts">
	import type { Artist } from "$lib/types/spotify";
	import Image from "./Image.svelte";

  export let artist: Artist;
  export let borderRadius = "100%";

</script>

<Image image={artist.images?.at(0)?.url} 
       imageAlt={artist.name} 
       borderRadius={borderRadius} 
       fallbackSvg="artist"/>

<style lang="scss">

</style>