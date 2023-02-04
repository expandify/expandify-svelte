<script lang="ts">
	import { fade } from 'svelte/transition';


</script>

<div class="cards" in:fade>
  <slot></slot>
</div>

<style lang="scss">

	.cards {
		display: grid;
    grid-template-columns: repeat(auto-fit, minmax(11rem, 1fr));
    gap: 2rem;
    justify-content: space-between;
		
	}
</style>
