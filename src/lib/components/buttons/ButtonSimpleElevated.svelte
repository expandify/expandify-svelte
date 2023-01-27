
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
    background-color: var(--background-elevated-base);
    cursor: pointer;
    color: var(--text-base);
    font-weight: 600;
    display: flex;
    justify-content: center;
    border-radius: 100rem;
    outline: none;
    border: none;
    padding: 0 2rem ;
    margin: 0.5rem;

    .content {
      margin-top: auto;
      margin-bottom: auto;
    }
  }
  .spotify-button:hover {
    background-color: var(--background-elevated-highlight);

    //outline: 0.1rem solid var(--text-base);
  }
</style>
