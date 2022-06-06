<script>
  import {MenuIcon} from "svelte-feather-icons"
  import LogoutButton from "../elements/LogoutButton.svelte";
  import ThemePicker from "../elements/ThemePicker.svelte";
  import AccentPicker from "../elements/AccentPicker.svelte";
  import {clickOutside} from "../../directives/clickOutside.js";
  import {session} from "$app/stores";
  import MenuEntry from "../elements/MenuEntry.svelte";

  let menuOpen = false

</script>

<div class="menu-bar">
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


  <a href="/" class="title">Exportify</a>
</div>

<style lang="scss">

  .menu-bar {
    display: flex;
    flex-direction: row;
    align-items: center;
    background-color: var(--bg-main-100);
    height: 4rem;
    width: 100%;
    border-bottom: 0.2rem solid var(--accent);

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
