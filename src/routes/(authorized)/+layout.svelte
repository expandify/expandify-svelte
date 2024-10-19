<script lang="ts">
	import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import DependencyModal from '$lib/components/layout/DependencyModal.svelte';
	import SideBar from '$lib/components/layout/SideBar.svelte';
	import { albums } from '$lib/stores/library/albums';
	import { artists } from '$lib/stores/library/artists';
	import { playlists } from '$lib/stores/library/playlists';
	import { tracks } from '$lib/stores/library/tracks';
	import { user } from '$lib/stores/library/user';
	import { spotifySession } from '$lib/stores/spotifySession';
	import { spotifyPersistence } from '$lib/services/spotify/spotify-persistance';


	$: if (!($spotifySession) && browser) {
		goto("/");
	}

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


<div class="flex flex-row min-h-lvh">
	{#if $spotifySession}
		<SideBar />
		<div class="modal-wrapper">
			<DependencyModal />
		</div>
		<main class="overflow-x-auto box-content flex flex-col p-8 w-full">
			<slot />
		</main>
	{/if}
</div>