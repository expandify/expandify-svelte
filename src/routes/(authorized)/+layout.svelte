<script lang="ts">
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import LoadingDependencyModal from '$lib/components/loading/LoadingDependencyModal.svelte';
	import NavBar from '$lib/components/navbar/NavBar.svelte';
	import { Spotify } from '$lib/data/spotify';
	import { albums } from '$lib/stores/library/albums';
	import { artists } from '$lib/stores/library/artists';
	import { playlists } from '$lib/stores/library/playlists';
	import { tracks } from '$lib/stores/library/tracks';
	import { user } from '$lib/stores/library/user';
	import { spotifySession } from '$lib/stores/spotifySession';
	
	
	$: if (!($spotifySession) && browser) goto("/");
/*
	
	if (!$albums.updated && browser) {
		Spotify.Album.loadSavedToStore();
	}


	if (!$artists.updated && browser) {
		Spotify.Artist.loadFollowedToStore();
	}

	if (!$playlists.updated && browser) {
		Spotify.Playlist.loadAllToStore();
	}

	if (!$tracks.updated && browser) {
		Spotify.Track.loadSavedToStore();
	}

	if (!$user.updated && browser) {
		Spotify.User.loadToStore();
	}
*/
</script>



<div class="page">
{#if $spotifySession}		
	<NavBar />
	
	<div class="modal-wrapper">
		<LoadingDependencyModal />		
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
		width: 100%;
		
		
		.content {									
			box-sizing: border-box;
			padding: 2rem 2rem 2rem 2rem;
			width: 100%;						
		}
	}
</style>
