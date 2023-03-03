<script lang="ts">
  import type { Album, Artist, Playlist } from '$lib/types/spotify';
	import { fade } from 'svelte/transition';
    import Card from '../common/Card.svelte';

  export let cards: Album[] | Artist[] | Playlist[];
</script>

<div class="cards" in:fade>
  {#each cards as card}
		<Card card={card}/>
	{/each}
</div>

<style lang="scss">

	.cards {
		display: grid;
    grid-template-columns: repeat(auto-fit, minmax(11rem, 1fr));
    gap: 2rem;
    justify-content: space-between;		
	}
</style>
