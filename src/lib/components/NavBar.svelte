<script lang="ts">
	import { reloadSavedAlbumsWithTracks } from '$lib/spotify/album';
	import { reloadFollowedArtists } from '$lib/spotify/artist';
	import { reloadUserPlaylistsWithTracks } from '$lib/spotify/playlist';
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon, DownloadIcon } from 'svelte-feather-icons';
	import { realoadSavedTracks } from '$lib/spotify/track';
	import { clearAuthData } from '$lib/stores/authData';
	import { Cache } from '$lib/stores/cache';
	import { state } from '$lib/stores/state';
	import IconLink from './IconLink.svelte';

	function reloadLibrary() {
		reloadUserPlaylistsWithTracks();
		reloadSavedAlbumsWithTracks();
		realoadSavedTracks();
		reloadFollowedArtists();
	}

	function logout() {
		clearAuthData();
		Cache.clearAll();
	}  
</script>

<aside class="container">
	<nav>
    <ul>
      <li><h3><a class="title" href="/">Expandify</a></h3></li>
    </ul>
		<section>
			<ul>
				<li><h5 class="heading">Library</h5></li>
				<hr class="divider" />
        <li><IconLink icon={DiscIcon} href="/library/album">Albums</IconLink></li>
        <li><IconLink icon={UsersIcon} href="/library/artist">Artists</IconLink></li>
        <li><IconLink icon={ListIcon} href="/library/playlist">Playlists</IconLink></li>
        <li><IconLink icon={MusicIcon} href="/library/track">Tracks</IconLink></li>
			</ul>
		</section>
		<section>
			<ul>
				<li><h5 class="heading">Information</h5></li>
				<hr class="divider" />
        <li><IconLink icon={DiscIcon} href="/information/album">Albums</IconLink></li>
        <li><IconLink icon={UsersIcon} href="/information/artist">Artists</IconLink></li>
        <li><IconLink icon={MusicIcon} href="/information/track">Tracks</IconLink></li>
			</ul>
		</section>
		<section>
			<ul>
				<li><h5 class="heading">Tools</h5></li>
				<hr class="divider" />
        <li><IconLink icon={MusicIcon} href="/tools/song-finder">Song Finder</IconLink></li>
        <li><IconLink icon={DownloadIcon} href="/tools/backup">Backup</IconLink></li>
			</ul>
		</section>
		<section>
			<ul>
				<li><button class="outline" on:click={reloadLibrary}>Reload Library</button></li>
				{#if $state.authenticated}
					<li><button class="secondary outline" on:click={logout}>Logout</button></li>
				{/if}
			</ul>
		</section>
	</nav>
</aside>

<style lang="scss">
	::-webkit-scrollbar {
		width: 0.3rem;
		height: 0.3rem;
	}
	::-webkit-scrollbar-track {
		border-radius: 0.2rem;
	}
	::-webkit-scrollbar-thumb {
		border-radius: 0.2rem;
		background: var(--range-thumb-color);
	}
	::-webkit-scrollbar-thumb:hover {
		background: var(--range-thumb-hover-color);
	}
	::-webkit-scrollbar-thumb:active {
		background: var(--range-thumb-active-color);
	}
	.container {
		height: 100%;
		padding: 2rem;
    padding-bottom: 0;
		overflow-y: auto;
		height: 100vh;

    .title {
      color: var(--h3-color);
    }

    .title:hover {
      color: var(--h6-color);
    }

		.heading {
			margin-bottom: 0rem;
		}
		.divider {
			border-radius: 1rem;
			border-top: 0.1rem solid var(--secondary);
      width: 95%;
		}
	}
</style>
