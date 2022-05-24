<script>
  import {themeStore} from "../../../stores/theme.js";
  import {setDarkMode, setAccent, SUPPORTED_ACCENTS} from "../../../stores/theme.js";
  import {SunIcon, MoonIcon, SettingsIcon} from 'svelte-feather-icons'

  let themeSelector = false
</script>

{#if themeSelector}
  <div class="themes">
    {#each SUPPORTED_ACCENTS as accent }
      <span class="theme accent-{accent}" on:click={() => setAccent(accent)}></span>
    {/each}
  </div>
{/if}

<div class="icons">
  <div class="icon" on:click={() => setDarkMode(!$themeStore.darkMode)}>
    {#if $themeStore.darkMode}
      <SunIcon/>
    {:else }
      <MoonIcon/>
    {/if}
  </div>
  <div class="icon" on:click={() => themeSelector = !themeSelector}>
    <SettingsIcon/>
  </div>
</div>



<style lang="scss">

  .icons {
    margin-top: 0.5rem;
    margin-bottom: 0.5rem;
    display: flex;
    flex-direction: row;
    justify-content: space-around;

    .icon {
      color: var(--text-900);
    }

    .icon:hover, {
      color: var(--accent);
      cursor: pointer;
    }
  }

  .themes {

    margin-top: 0.5rem;
    margin-bottom: 0.5rem;
    background-color: var(--bg-main-100);
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    span {
      display: inline-block;
      height: 1.5rem;
      width: 1.5rem;
      border-radius: 5rem;
      background-color: var(--accent);
      border: 0.2rem solid var(--bg-main-100);
    }

    span:hover {
      border: 0.2rem solid var(--bg-main-50);
      cursor: pointer;
    }
  }

</style>