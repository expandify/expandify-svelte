<script>
  import LibrarySwitcher from "$lib/client/components/LibrarySwitcher.svelte";
  import { session } from "$app/stores";
  import SideBar from "$lib/client/components/SideBar.svelte";
</script>

<div class="wrapper">
  {#if $session.loggedIn}
    <div class="sidebar"><SideBar /></div>
  {/if}
  <div class="library">
    <LibrarySwitcher />
    <div class="slot">
      <slot />
    </div>
  </div>
</div>

<style lang="scss">
  .wrapper {
    display: flex;
    flex-direction: row;
    width: 100%;

    .library {
      width: 100%;

      .slot {
        width: 100%;
        box-sizing: border-box; 
        padding: 2rem;
      }
    }
  }
</style>
