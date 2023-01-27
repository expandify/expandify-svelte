<script lang="ts">
	import Card from '$lib/components/common/Card.svelte';
	import { fade } from 'svelte/transition';

  export let cards: {
    title: string, 
    subtitle?: string | null, 
    image?: string | null, 
    href?: string | null, 
    fallbackSvg: string}[],
    imgRounded: "full" | "little" | "none" = "little";

</script>

<div class="cards" in:fade>
  {#each cards as card}
    <Card title={card.title} 
          subtitle={card.subtitle} 
          href={card.href}
          image={card.image}
          fallbackSvg={card.fallbackSvg}
          imgRounded={imgRounded}/>
  {/each}
</div>

<style lang="scss">

	.cards {
		display: grid;
    grid-template-columns: repeat(auto-fit, minmax(11rem, 1fr));
    gap: 1rem;
    justify-content: space-between;
		
	}
</style>
