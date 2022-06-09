<script>
  import {setAccent, SUPPORTED_ACCENTS} from "../../stores/theme";
  import {SettingsIcon} from 'svelte-feather-icons'
  import MenuEntry from "./IconLink.svelte";

  let themeSelector = false
</script>

<div>

  <div on:click={() => themeSelector = !themeSelector}>
    <MenuEntry icon={SettingsIcon}>Accent Color</MenuEntry>
  </div>

  {#if themeSelector}
    <div class="themes">
      {#each SUPPORTED_ACCENTS as accent }
        <span class="theme accent-{accent}" on:click={() => setAccent(accent)}></span>
      {/each}
    </div>
  {/if}

</div>

<style lang="scss">

  .themes {

    margin-top: 1rem;


    display: flex;
    flex-direction: row;
    justify-content: space-between;

    span {
      display: inline-block;
      height: 1.5rem;
      width: 1.5rem;
      border-radius: 5rem;
      background-color: var(--accent);
      border: 0.2rem solid var(--bg-main-50);
    }

    span:hover {
      border: 0.2rem solid var(--bg-main-100);
      cursor: pointer;
    }
  }

</style>