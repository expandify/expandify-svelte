<script lang="ts">
	import { createEventDispatcher } from "svelte";

  const dispatch = createEventDispatcher();  

</script>

<button class="spotify-button"  on:click={() => dispatch('click')}>
  <div class="content">
    <slot/>
  </div>
</button>

<style lang="scss">
  .spotify-button {
    background-color: var(--spotify-green);
    cursor: pointer;
    color: var(--background-base);
    font-weight: 600;
    display: flex;
    justify-content: center;
    border-radius: 100rem;
    padding: 0 2rem ;
    margin: 0.5rem;


    .content {
      margin-top: auto;
      margin-bottom: auto;
    }
  }
  .spotify-button:hover {
    background-color: var(--spotify-green-hover);
  }
</style>
