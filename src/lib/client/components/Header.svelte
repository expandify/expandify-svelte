<script lang="ts">
  import {MenuIcon} from "svelte-feather-icons"
  import LogoutButton from "./LogoutButton.svelte";
  import ThemePicker from "./ThemePicker.svelte";
  import AccentPicker from "./AccentPicker.svelte";
  import {clickOutside} from "$lib/client/directives/clickOutside";
  import {session} from "$app/stores";
  import MenuEntry from "./IconLink.svelte";

  let menuOpen = false

</script>

<div class="menu-bar">
  <a href="/" class="title">Exportify</a>
  <div class="dropdown-menu">
    {#if !menuOpen}
      <div class="menu-icon" on:click={() => menuOpen = true}>
        <MenuEntry icon={MenuIcon}/>
      </div>
    {:else}
      <div class="menu-icon">
        <MenuEntry icon={MenuIcon}/>
      </div>

      <div class="dropdown-content" use:clickOutside on:click_outside={() => menuOpen = false}>
        <div class="dropdown-selector">
          <ThemePicker/>
        </div>

        <div class="dropdown-selector">
          <AccentPicker/>
        </div>

        {#if $session.loggedIn}
          <div class="dropdown-selector">
            <LogoutButton/>
          </div>
        {/if}

      </div>
    {/if}
  </div>
</div>

<style lang="scss">

  .menu-bar {
    display: flex;
    flex-direction: row;
    align-items: center;
    background-color: var(--bg-main-100);
    height: 4rem;
    width: 100%;

    .dropdown-menu {

      .menu-icon {
        padding: 1rem;
      }

      .dropdown-content {
        display: flex;
        flex-direction: column;
        background-color: var(--bg-main-50);
        position: absolute;
        margin-left: 1rem;
        z-index: 1;
        right: 2rem;
        .dropdown-selector {
          padding: 1rem;
        }

      }
    }

    .title {
      margin-right: auto;
      margin-left: auto;
      font-size: 2rem;
    }
  }

</style>
