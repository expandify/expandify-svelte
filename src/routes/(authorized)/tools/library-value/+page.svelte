<script lang="ts">
	import ButtonSimple from "$lib/components/buttons/ButtonSimple.svelte";
	import ButtonSpotify from "$lib/components/buttons/ButtonSpotify.svelte";
	import { calculateLibraryValue } from "./value-calculator";

  
</script>

<p>Track The Newest Releases Of Your Artists</p>
<span>Under Construction</span>
<ButtonSpotify on:click={calculateLibraryValue}><h2>Calculate Library Value</h2></ButtonSpotify>


<style lang="scss">
</style>
