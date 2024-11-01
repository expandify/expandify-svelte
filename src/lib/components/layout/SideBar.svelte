<script lang="ts">
	import * as Sidebar from '$lib/components/ui/sidebar/index.js';
	import { logout } from '$lib/auth/auth';
	import { spotifyPersistence } from '$lib/services/spotify/spotify-persistance';
	import { toggleMode } from 'mode-watcher';
	import { AudioLines, Disc3, FolderSync, ListMusic, LogOut, Moon, SquareUser, Sun } from 'lucide-svelte';

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


<Sidebar.Provider>
	<Sidebar.Root variant="inset">
		<Sidebar.Header>

		</Sidebar.Header>
		<Sidebar.Content>
			<Sidebar.Group>
				<Sidebar.GroupLabel>Library</Sidebar.GroupLabel>
				<Sidebar.GroupContent>
					<Sidebar.Menu>
						{#each library as lib (lib.title)}
							<Sidebar.MenuItem>
								<Sidebar.MenuButton>
									{#snippet child({ props })}
										<a href={lib.url} {...props}>
											<lib.icon />
											<span>{lib.title}</span>
										</a>
									{/snippet}
								</Sidebar.MenuButton>
							</Sidebar.MenuItem>
						{/each}
					</Sidebar.Menu>
				</Sidebar.GroupContent>
			</Sidebar.Group>
			<Sidebar.Separator />
			<Sidebar.Group>
				<Sidebar.GroupLabel>Library</Sidebar.GroupLabel>
				<Sidebar.GroupContent>
					<Sidebar.Menu>
						{#each tools as tool (tool.title)}
							<Sidebar.MenuItem>
								<Sidebar.MenuButton>
									{#snippet child({ props })}
										<a href={tool.url} {...props}>
											<tool.icon />
											<span>{tool.title}</span>
										</a>
									{/snippet}
								</Sidebar.MenuButton>
							</Sidebar.MenuItem>
						{/each}
					</Sidebar.Menu>
				</Sidebar.GroupContent>
			</Sidebar.Group>
			<Sidebar.Separator />
		</Sidebar.Content>

		<Sidebar.Separator />
		<Sidebar.Footer>
			<Sidebar.Menu>
				<Sidebar.MenuItem>
					<Sidebar.MenuButton>
						{#snippet child({ props })}
							<button onclick={() => spotifyPersistence.reloadLibrary()} {...props}>
								<FolderSync />
								<span>Refresh Library</span>
							</button>
						{/snippet}
					</Sidebar.MenuButton>
				</Sidebar.MenuItem>
				<Sidebar.MenuItem>
					<Sidebar.MenuButton>
						{#snippet child({ props })}
							<button onclick={toggleMode} {...props}>
								<Moon class="rotate-0 scale-100 dark:-rotate-90 dark:scale-0" />
								<Sun class="absolute rotate-90 scale-0 dark:rotate-0 dark:scale-100" />
								<span>Toggle theme</span>
							</button>
						{/snippet}
					</Sidebar.MenuButton>
				</Sidebar.MenuItem>
				<Sidebar.MenuItem>
					<Sidebar.MenuButton>
						{#snippet child({ props })}
							<button onclick={logout} {...props}>
								<LogOut />
								<span>Logout</span>
							</button>
						{/snippet}
					</Sidebar.MenuButton>
				</Sidebar.MenuItem>
			</Sidebar.Menu>
		</Sidebar.Footer>
		<Sidebar.Rail />
	</Sidebar.Root>
	<Sidebar.Inset>
	<main class="p-6">

		{@render children?.()}
	</main>
	</Sidebar.Inset>
</Sidebar.Provider>
