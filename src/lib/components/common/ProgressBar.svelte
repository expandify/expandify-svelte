<script lang="ts">
	let { max = null, value = null } = $props<{
		max?: number | null;
		value?: number | null;
	}>();
</script>

{#if value && max}
	<progress {value} {max}></progress>
{:else}
	<progress></progress>
{/if}


<style lang="scss">

  progress {
    border-radius: 0.5rem;
    height: 0.5rem;
    width: 100%;
  }

  progress::-webkit-progress-bar {
    background-color: var(--background-elevated-highlight);
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