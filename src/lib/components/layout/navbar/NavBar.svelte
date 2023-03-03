<script lang="ts">
	import NavBarSection from './NavBarSection.svelte';
	import { Spotify } from '$lib/data/spotify';
	import { logout } from '$lib/auth/auth';
	import { goto } from '$app/navigation';
	import Svg from '$lib/components/common/Svg.svelte';

	function logouts() {
		logout();
    goto("/");
	} 
</script>

<aside class="sidebar">
	<div class="nav">
		<nav>	
			<h2><a class="title" href="/dashboard">Expandify</a></h2>
			<NavBarSection title="Library">
				<a href="/library/album">Albums</a>
				<a href="/library/artist">Artists</a>
				<a href="/library/playlist">Playlists</a>
				<a href="/library/track">Tracks</a>
			</NavBarSection>
			<NavBarSection title="Tools">
				<a href="/tools/release-tracker">Release Tracker</a>
				<a href="/tools/song-finder">Song Finder</a>
				<a href="/tools/library-value">Library Value</a>
				<a href="/tools/backup">Backup</a>  
			</NavBarSection>		
		</nav>	
	</div>	
	<div class="buttons">
		<button class="button" on:click={logouts} title="Logout">
			<Svg name={"logout"} />
		</button> 
		<button class="button" on:click={Spotify.loadLibraryToStores} title="Reload Library">
			<Svg name={"reload"} />
		</button> 				
	</div>		
</aside>

<style lang="scss">
	::-webkit-scrollbar {
		width: 0.6rem;
		height: 0.3rem;
	}
	::-webkit-scrollbar-track {
		border-radius: 10rem;
	}
	::-webkit-scrollbar-thumb {
		border-radius: 10rem;
		background: var(--background-elevated-base);
	}
	::-webkit-scrollbar-thumb:hover {
		background: var(--background-elevated-highlight);
	}
	::-webkit-scrollbar-thumb:active {
		background: var(--text-subdued);
	}

	.sidebar {		
		display: flex;
		flex-direction: column;
		background-color: var(--background-press);		
		box-sizing: border-box;		
		position: sticky;
		position: -webkit-sticky;
		top: 0;
		left: 0;		
		height: 100vh;
		min-width: 18rem;

		.nav {
			padding: 2rem;
			overflow-y: auto;					
			display: flex;
			height: 100%;
			flex-direction: column;			
			justify-content: flex-start;

			
			.title {
				display: flex;
				flex-direction: row;
				justify-content: start;
				align-items: center;
				gap: 0.5rem;
				text-decoration: none;
      	color: var(--text-base);
				margin-bottom: 0;
			}			
		}
		.buttons {
			border-top: 0.2rem solid var(--background-highlight);
			padding: 1rem;
			display: flex;
			flex-direction: row;			
			justify-content: space-between;
			

			.button {
				background-color: inherit;        
        border: none;
        fill: var(--text-subdued);
				cursor: pointer;
				width: 2.5rem;
			}
			.button:hover {                
        fill: var(--text-base);      
      }
		}
    
	}
</style>
