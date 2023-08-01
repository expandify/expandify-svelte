<script lang="ts">


  let className = "";
  export { className as class };
  export let skeleton = false;

  export let length = "5rem"

</script>

{#if !skeleton}
  <span class={className}><slot /></span>
{:else }
  <div class="{className} skeleton" style="width: {length}"></div>
{/if}


<style lang="scss">

  .skeleton {
    background-color: var(--background-elevated-highlight);
    height: 1rem;
    border-radius: 10rem;
    margin: 0.1rem 0;
  }
</style>