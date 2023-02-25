<script lang="ts">
	import LibraryNavBarSection from './LibraryNavBarSection.svelte';
	import ToolsNavBarSection from './ToolsNavBarSection.svelte';
	import ButtonNavBarSection from './ButtonNavBarSection.svelte';

</script>

<aside class="sidebar">
	<div class="nav">
		<nav>	
			<h2><a class="title" href="/dashboard">Expandify</a></h2>
			<LibraryNavBarSection/>
			<ToolsNavBarSection/>				
		</nav>	
	</div>	
	<div class="buttons">
		<ButtonNavBarSection/>
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
		width: 18rem;

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
			padding: 0.5rem 2rem 1rem;
		}
    
	}
</style>
