<script>
  export let icon;
  export let href;
</script>

<a class="menu-entry" href={href}>
  <svelte:component this={icon} class="icon"/>
  <slot></slot>
</a>


<style lang="scss">

  a.menu-entry {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;

    // Needs the :global modifier, because classes cannot be passed down to child components.
    // Together with the scoped .menu-list > li, this will still be scoped
    :global(.icon) {
      stroke-width: 3;
      width: 1.1rem;
      height: 1.1rem;
      margin-right: 1rem;
    }
  }


</style>