<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import CardGrid from '$lib/components/layout/CardGrid.svelte';
	import { Playlists, playlistStore } from '$lib/stores/library/playlists';

	function toCards(playlists: Playlist[]) {
		return playlists.map(playlist => ({
			title: playlist.name,
			subtitle: `By ${playlist.owner.display_name}`,
			image: playlist.images.at(0)?.url,
			fallbackSvg: "playlist",
			href: null
		}))
	}
	$: playlists = toCards($playlistStore.playlists);

</script>

<div class="header">
	<h2>Playlists - {playlists.length}</h2>
	<ButtonSimpleElevated on:click={Playlists.loadAll}>Reload Playlists</ButtonSimpleElevated>
</div>

<h3>Playlists - {playlists.length}</h3>
<CardGrid cards={playlists} />

<h3>Playlists - {playlists.length}</h3>
<CardGrid cards={playlists.slice(1,2)} />

<style lang="scss">

	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
