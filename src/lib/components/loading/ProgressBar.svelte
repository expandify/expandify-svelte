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
    border-radius: 0.5rem; 
    height: 0.5rem;
    width: 100%;
  }
  progress::-webkit-progress-bar {
    background-color: var(--background-highlight);
    border-radius: 0.5rem;
  }
  progress::-webkit-progress-value {
    background-color: var(--text-subdued);
    border-radius: 0.5rem;
  }

  /*
  progress::-moz-progress-bar {    
  }
  */
</style>