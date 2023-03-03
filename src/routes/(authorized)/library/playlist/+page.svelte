<script lang="ts">
	import CardGrid from '$lib/components/layout/CardGrid.svelte';
	import { dependencies } from '$lib/stores/dependencies';
	import { playlists } from '$lib/stores/library/playlists';

	dependencies.onlyPlaylistsNeeded();
</script>


<h2>Playlists - {$playlists.playlists.length}</h2>
<CardGrid cards={$playlists.playlists}/>

<style lang="scss">

</style>
