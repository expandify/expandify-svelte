<script lang="ts">
	import CardGrid from '$lib/components/layout/CardGrid.svelte';
	import { albums } from '$lib/stores/library/albums';
	import {dependencies} from "$lib/stores/dependencies";
	import Card from '$lib/components/common/Card.svelte';
    import Button from "$lib/components/common/Button.svelte";

	
	dependencies.onlyAlbumsNeeded();
	

</script>


<h2>Albums - {$albums.albums.length}</h2>
<CardGrid cards={$albums.albums}/>

<style lang="scss">

</style>
