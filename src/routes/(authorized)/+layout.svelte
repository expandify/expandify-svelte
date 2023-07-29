<script lang="ts">
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import DependencyModal from '$lib/components/layout/DependencyModal.svelte';
	import NavBar from '$lib/components/layout/navbar/NavBar.svelte';
	import { albums } from '$lib/stores/library/albums';
	import { artists } from '$lib/stores/library/artists';
	import { playlists } from '$lib/stores/library/playlists';
	import { tracks } from '$lib/stores/library/tracks';
	import { user } from '$lib/stores/library/user';
	import { spotifySession } from '$lib/stores/spotifySession';
	import { spotifyPersistence } from "$lib/services/spotify/spotify-persistance";
	
	
	$: if (!($spotifySession) && browser) goto("/");
	
	if (!$albums.updated && browser) {
		spotifyPersistence.loadSavedAlbums();
	}


	if (!$artists.updated && browser) {
		spotifyPersistence.loadFollowedArtists();
	}

	if (!$playlists.updated && browser) {
		spotifyPersistence.loadPlaylists();
	}

	if (!$tracks.updated && browser) {
		spotifyPersistence.loadSavedTracks();
	}

	if (!$user.updated && browser) {
		spotifyPersistence.loadUser();
	}

</script>



<div class="page">
{#if $spotifySession}	
	<NavBar />
	<div class="modal-wrapper">
		<DependencyModal />		
	</div>	
	<main class="content" >
		
		<slot />
	</main>			
		
		
	
{/if}
</div>

<style lang="scss">
	.page {
		display: flex;
		flex-direction: row;		
		min-height: 100vh;	
		
		
		.content {		
			overflow-x: auto;
			box-sizing: content-box;
			display: flex;
			flex-direction: column;
			padding: 2rem 2rem 2rem 2rem;
			width: 100%;						
		}
	}
</style>
