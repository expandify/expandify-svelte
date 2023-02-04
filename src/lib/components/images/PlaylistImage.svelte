<script lang="ts">
	import Image from "./Image.svelte";

  export let playlist: PlaylistSimplified;
  export let borderRadius = "1rem";

</script>

<Image image={playlist.images.at(0)?.url} 
       imageAlt={playlist.name} 
       borderRadius={borderRadius} 
       fallbackSvg="playlist"/>

<style lang="scss">

</style>