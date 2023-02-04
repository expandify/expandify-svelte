<script lang="ts">
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