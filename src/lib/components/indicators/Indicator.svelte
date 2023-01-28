<script lang="ts">
	import { onMount } from 'svelte';
  import { fade } from 'svelte/transition';

  export let color: string = "--text-base";
  export let timeout: number = 1000;
  export let alwaysVisible: boolean = false;

  let visible = true;

  onMount(() => setTimeout(() => visible = false, timeout));

</script>

{#if alwaysVisible || visible}
<article id=indicator style="color: {color}" out:fade>
	<slot></slot>
</article>
{/if}


<style lang="scss">
	#indicator {
    padding: 1rem;
    min-width: 10rem;

    background-color: var(--background-elevated-highlight);
    border-radius: var(--border-radius);

    box-shadow: 0 0 0.5rem 0rem var(--background-elevated-highlight);
  }
</style>
