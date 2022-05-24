<script>
  export let icon;
  export let href = null;

</script>

{#if href === null}
  <div class="menu-entry">
    <svelte:component this={icon} class="icon"/>
    <slot></slot>
  </div>
{:else}
<a class="menu-entry" href={href}>
  <svelte:component this={icon} class="icon"/>
  <slot></slot>
</a>
{/if}

<style lang="scss">

  .menu-entry {
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

  .menu-entry:hover {
    color: var(--accent);
    cursor: pointer;
  }

</style>