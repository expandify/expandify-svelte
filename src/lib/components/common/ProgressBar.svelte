<script lang="ts">
  export let max: number | null = null;
  export let value: number | null = null;
</script>

{#if value && max}
<progress {value} {max} />  
{:else}
<progress/>  
{/if}



<style lang="scss">

  progress {
    border-radius: var(--border-radius); 
    height: 0.5rem;
    width: 100%;
  }
  progress::-webkit-progress-bar {
    background-color: var(--background-highlight);
    border-radius: var(--border-radius);
  }
  progress::-webkit-progress-value {
    background-color: var(--text-subdued);
    border-radius: var(--border-radius);
  }

  progress::-moz-progress-bar {
    /* style rules */
  }
</style>