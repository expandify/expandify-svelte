
<script lang="ts">
	import { createEventDispatcher } from "svelte";
  const dispatch = createEventDispatcher();  

  export let style: 'base' | 'elevated' | 'spotify' = "base";
  export let text: string;
</script>

<button class="button"
        on:click={() => dispatch('click')}
        class:base={style === 'base'}
        class:elevated={style === 'elevated'}
        class:spotify={style === 'spotify'}>
  <div class="content">
    {text}
  </div>
</button>

<style lang="scss">
  .button {    
    cursor: pointer;    
    border-radius: 100rem;
    outline: none;
    border: none;
    padding: 1rem 2.5rem ;
    width: 100%;    

    .content {
      font-weight: 600;      
    }
  }


  .base {
    background-color: var(--background-base);
    color: var(--text-base);
  }

  .base:hover {
    background-color: var(--background-highlight);
  }

  .elevated {
    background-color: var(--background-elevated-base);
    color: var(--text-base);
  }

  .elevated:hover {
    background-color: var(--background-elevated-highlight);
  }

  .spotify {
    background-color: var(--spotify-green);
    color: var(--background-base);
  }

  .spotify:hover {
    background-color: var(--spotify-green-hover);
  } 

</style>
