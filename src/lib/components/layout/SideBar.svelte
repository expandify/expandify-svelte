<script lang="ts">
	import { logout } from '$lib/auth/auth';
	import { spotifyPersistence } from '$lib/services/spotify/spotify-persistance';
	import { toggleMode } from 'mode-watcher';
	import { AudioLines, Disc3, FolderSync, ListMusic, LogOut, Moon, Music2, SquareUser, Sun } from 'lucide-svelte';
	import WebPlayer from '$lib/components/dashboard/WebPlayer.svelte';

	import { Navigation } from '@skeletonlabs/skeleton-svelte';
	// Not Nice, but otherwise WebStorm is annoying.
	const NavigationRail = Navigation.Rail;
	const NavigationTile = Navigation.Tile;

	let { children } = $props<{
		children?: import('svelte').Snippet;
	}>();


	const library = [
		{
			title: 'Albums',
			url: '/library/album',
			icon: Disc3
		},
		{
			title: 'Artists',
			url: '/library/artist',
			icon: SquareUser
		},
		{
			title: 'Playlists',
			url: '/library/playlist',
			icon: ListMusic
		},
		{
			title: 'Tracks',
			url: '/library/track',
			icon: AudioLines
		}
	];

	const tools = [
		{
			title: 'Release Tracker',
			url: '/tools/release-tracker',
			icon: Disc3
		},
		{
			title: 'Song Finder',
			url: '/tools/song-finder',
			icon: SquareUser
		},
		{
			title: 'Library Value',
			url: '/tools/library-value',
			icon: ListMusic
		},
		{
			title: 'Cover Flow',
			url: '/tools/cover-flow',
			icon: AudioLines
		},
		{
			title: 'Backup',
			url: '/tools/backup',
			icon: AudioLines
		}
	];
</script>
<div class="card border-surface-100-900 grid w-full grid-cols-[auto_1fr]">
	<NavigationRail expanded
									classes="h-screen sticky top-0 justify-start overflow-auto"
									tilesBase="flex-0 mb-auto"
									headerGap="mb-12"
	>
		{#snippet header()}
			<NavigationTile href="/dashboard" labelExpanded="Exportify" >
				<Music2 />
			</NavigationTile>
		{/snippet}
		{#snippet tiles()}
			<hr class="hr border-t-2" />
			{#each library as lib (lib.title)}
				{@const Icon = lib.icon}
				<NavigationTile href={lib.url} labelExpanded={lib.title} label={lib.title} >
					<Icon />
				</NavigationTile>
			{/each}
			<hr class="hr border-t-2" />
			{#each tools as tool (tool.title)}
				{@const Icon = tool.icon}
				<NavigationTile href={tool.url} labelExpanded={tool.title} label={tool.title}>
					<Icon />
				</NavigationTile>
			{/each}
			<hr class="hr border-t-2" />
		{/snippet}

		{#snippet footer()}

			<hr class="hr border-t-2" />
			<div class="w-full p-2">
				<WebPlayer></WebPlayer>
			</div>
			<hr class="hr border-t-2" />
			<NavigationTile onclick={() => spotifyPersistence.reloadLibrary()} labelExpanded="Refresh Library">
					<FolderSync />
			</NavigationTile>
			<NavigationTile id="Theme" onclick={toggleMode} labelExpanded="Theme">
				<Sun class="absolute rotate-90 scale-0 dark:rotate-0 dark:scale-100" />
				<Moon class="rotate-0 scale-100 dark:-rotate-90 dark:scale-0" />
			</NavigationTile>
			<NavigationTile onclick={logout} labelExpanded="Logout">
					<LogOut />
			</NavigationTile>
		{/snippet}
		<!--<Sidebar.Rail />-->
	</NavigationRail>
		<main class="p-6 overflow-auto">

			{@render children?.()}
		</main>
</div>