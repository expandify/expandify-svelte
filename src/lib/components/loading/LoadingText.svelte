<script lang="ts">
	import LoadingDots from "./LoadingDots.svelte";

  export let title: string;
  export let total: number | null = null;
  export let current: number | null = null;
  export let loading: boolean = true;

</script>

{#if loading}

  {#if !total || !current}
  <h1><LoadingDots message={`Loading ${title}`} /></h1>
  {:else}
  <h1>Loading {title}: {current} of {total}</h1>
  {/if}

{:else}
  {#if !total || !current}
  <h1>Loaded {title}</h1>
  {:else}
  <h1>Loaded {title}: {current} of {total}</h1>
  {/if}

{/if}


<style lang="scss"> 
</style>
