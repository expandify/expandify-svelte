<script lang="ts">
	import {
		DarkMode,
		Sidebar,
		SidebarBrand,
		SidebarDropdownWrapper,
		SidebarGroup,
		SidebarItem,
		SidebarWrapper, type SiteType
	} from 'flowbite-svelte';
	import {
		ChartPieSolid, ArrowLeftToBracketOutline, RefreshOutline
	} from 'flowbite-svelte-icons';
	import { logout } from '$lib/auth/auth';
	import { page } from '$app/stores';
	import { spotifyPersistence } from '$lib/services/spotify/spotify-persistance';

	let site: SiteType = {
		name: 'Exportify',
		href: '/',
		img: '/Icon.jpg'
	};

	$: activeUrl = $page.url.pathname;
</script>
<Sidebar {activeUrl} class="min-w-56  box-border">
	<SidebarWrapper class="flex flex-col justify-between h-lvh sticky top-0">
		<SidebarGroup>
			<SidebarBrand {site} />
			<SidebarGroup border>
				<SidebarDropdownWrapper label="Library" isOpen>
					<svelte:fragment slot="icon">
						<ChartPieSolid
							class="w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" />
					</svelte:fragment>
					<SidebarItem label="Albums" href="/library/album"></SidebarItem>
					<SidebarItem label="Artists" href="/library/artist"></SidebarItem>
					<SidebarItem label="Playlists" href="/library/playlist"></SidebarItem>
					<SidebarItem label="Tracks" href="/library/track"></SidebarItem>
				</SidebarDropdownWrapper>
			</SidebarGroup>
			<SidebarGroup border>
				<SidebarDropdownWrapper label="Tools" isOpen>
					<svelte:fragment slot="icon">
						<ChartPieSolid
							class="w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" />
					</svelte:fragment>
					<SidebarItem label="Release Tracker" href="/tools/release-tracker"></SidebarItem>
					<SidebarItem label="Song Finder" href="/tools/song-finder"></SidebarItem>
					<SidebarItem label="Library Value" href="/tools/library-value"></SidebarItem>
					<SidebarItem label="Backup" href="/tools/backup"></SidebarItem>
				</SidebarDropdownWrapper>
			</SidebarGroup>
		</SidebarGroup>
		<SidebarGroup>
			<DarkMode class="text-primary-500 dark:text-primary-600 border dark:border-gray-800" />
			<SidebarGroup border>
				<SidebarItem label="Refresh" on:click={() => spotifyPersistence.reloadLibrary()}>
					<svelte:fragment slot="icon">
						<RefreshOutline
							class="w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" />
					</svelte:fragment>
				</SidebarItem>
				<SidebarItem label="Sign Out" on:click={() => logout()} href="/">
					<svelte:fragment slot="icon">
						<ArrowLeftToBracketOutline
							class="w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" />
					</svelte:fragment>
				</SidebarItem>
			</SidebarGroup>
		</SidebarGroup>
	</SidebarWrapper>
</Sidebar>
