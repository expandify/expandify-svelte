<script lang="ts">
	import LoadingDots from "./LoadingDots.svelte";

  export let title: string;
  export let total: number | null = null;
  export let current: number | null = null;
  export let loading: boolean = true;
  export let error: boolean = false;

</script>

<div class="loading-text-container">
  {#if error}
    <h1 class="error">Error loading {title}</h1>
  {:else if loading}

    {#if !total || !current}
    <h1><LoadingDots message={`Loading ${title}`} /></h1>
    {:else}
    <h1>Loading {title}: {current} of {total}</h1>
    {/if}

  {:else}
    {#if !total || !current}
    <h1 class="success">Loaded {title}</h1>
    {:else}
    <h1 class="success">Loaded {title}: {current} of {total}</h1>
    {/if}
  {/if}

  <div class="slot">
    <slot />
  </div>
</div>
<style lang="scss"> 
  .loading-text-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 2rem;
    
    .error {
      color: var(--text-negative);
    }

    .success {
      color: var(--text-positive);    
    }
  }
  
</style>
