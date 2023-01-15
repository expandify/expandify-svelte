<script lang="ts">
  import { fade } from 'svelte/transition';

  export let color: string = "--text-base";
</script>

<article id=indicator style="color: {color}" out:fade>
	<slot></slot>
</article>

<style lang="scss">
	#indicator {
    padding: 1rem;
    min-width: 10rem;

    background-color: var(--background-elevated-highlight);
    border-radius: var(--border-radius);

    box-shadow: 0 0 0.5rem 0rem var(--background-elevated-highlight);
  }
</style>
