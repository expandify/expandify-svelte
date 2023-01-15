<script lang="ts">
  export let icon: any;
  export let href: string | null = "";
  export let func: () => void = () => {}
</script>


<a class="menu-entry" href={href} on:click={func}>
  <svelte:component this={icon} class="icon"/>
  <slot></slot>
</a>

<style lang="scss">

  .menu-entry {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;

    // Needs the :global modifier, because classes cannot be passed down to child components.
    // Together with the scoped .menu-list, this will still be scoped
    :global(.icon) {
      width: 1.5rem;
      height: 1.5rem;
      margin-right: 0.5rem;
    }
  }


</style>