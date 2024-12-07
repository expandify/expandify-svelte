<script lang="ts">
	import { logout } from '$lib/auth/auth';
	import { spotifyPersistence } from '$lib/services/spotify/spotify-persistance';
	import { toggleMode } from 'mode-watcher';
	import { AudioLines, Disc3, FolderSync, ListMusic, LogOut, Moon, SquareUser, Sun } from 'lucide-svelte';
	import WebPlayer from '$lib/components/dashboard/WebPlayer.svelte';
	import {
		Sidebar,
		SidebarContent, SidebarFooter, SidebarGroupContent, SidebarGroupLabel,
		SidebarHeader, SidebarInset, SidebarMenu, SidebarGroup,
		SidebarMenuButton, SidebarMenuItem,
		SidebarProvider, SidebarSeparator
	} from '$lib/components/ui/sidebar';


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


<SidebarProvider>
	<Sidebar variant="inset">
		<SidebarHeader>
			<SidebarMenuButton class="h-fit">
				<a href="/dashboard" class="text-3xl">Exportify</a>
			</SidebarMenuButton>
		</SidebarHeader>
		<SidebarContent>
			<SidebarGroup>
				<SidebarGroupLabel>Library</SidebarGroupLabel>
				<SidebarGroupContent>
					<SidebarMenu>
						{#each library as lib (lib.title)}
							<SidebarMenuItem>
								<SidebarMenuButton>
									{#snippet child({ props })}
										<a href={lib.url} {...props}>
											<lib.icon />
											<span>{lib.title}</span>
										</a>
									{/snippet}
								</SidebarMenuButton>
							</SidebarMenuItem>
						{/each}
					</SidebarMenu>
				</SidebarGroupContent>
			</SidebarGroup>
			<SidebarSeparator />
			<SidebarGroup>
				<SidebarGroupLabel>Library</SidebarGroupLabel>
				<SidebarGroupContent>
					<SidebarMenu>
						{#each tools as tool (tool.title)}
							<SidebarMenuItem>
								<SidebarMenuButton>
									{#snippet child({ props })}
										<a href={tool.url} {...props}>
											<tool.icon />
											<span>{tool.title}</span>
										</a>
									{/snippet}
								</SidebarMenuButton>
							</SidebarMenuItem>
						{/each}
					</SidebarMenu>
				</SidebarGroupContent>
			</SidebarGroup>
			<SidebarSeparator />
		</SidebarContent>


		<SidebarFooter>
			<SidebarSeparator />
			<SidebarGroup>
				<WebPlayer></WebPlayer>
			</SidebarGroup>
			<SidebarSeparator />
			<SidebarMenu>
				<SidebarMenuItem>
					<SidebarMenuButton>
						{#snippet child({ props })}
							<button onclick={() => spotifyPersistence.reloadLibrary()} {...props}>
								<FolderSync />
								<span>Refresh Library</span>
							</button>
						{/snippet}
					</SidebarMenuButton>
				</SidebarMenuItem>
				<SidebarMenuItem>
					<SidebarMenuButton>
						{#snippet child({ props })}
							<button onclick={toggleMode} {...props}>
								<Moon class="rotate-0 scale-100 dark:-rotate-90 dark:scale-0" />
								<Sun class="absolute rotate-90 scale-0 dark:rotate-0 dark:scale-100" />
								<span>Toggle theme</span>
							</button>
						{/snippet}
					</SidebarMenuButton>
				</SidebarMenuItem>
				<SidebarMenuItem>
					<SidebarMenuButton>
						{#snippet child({ props })}
							<button onclick={logout} {...props}>
								<LogOut />
								<span>Logout</span>
							</button>
						{/snippet}
					</SidebarMenuButton>
				</SidebarMenuItem>
			</SidebarMenu>
		</SidebarFooter>
		<!--<Sidebar.Rail />-->
	</Sidebar>
	<SidebarInset class="overflow-hidden">
		<main class="p-6 ">

			{@render children?.()}
		</main>
	</SidebarInset>
</SidebarProvider>