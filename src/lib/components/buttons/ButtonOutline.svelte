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
    background-color: var(--background-press);
    cursor: pointer;
    color: var(--text-base);
    font-weight: 600;
    display: flex;
    justify-content: center;
    border-radius: 100rem;

    outline: 0.1rem solid var(--text-subdued);
    margin: 0.5rem;

    .content {
      margin-top: auto;
      margin-bottom: auto;
    }
  }
  .spotify-button:hover {
    outline: 0.1rem solid var(--text-base);
  }
</style>