<script>
  import {themeStore} from "../../../stores/theme.js";
  import {setDarkMode} from "../../../stores/theme.js";
  import {SunIcon, MoonIcon} from 'svelte-feather-icons'
  import MenuEntry from "./MenuEntry.svelte";

</script>

  <div on:click={() => setDarkMode(!$themeStore.darkMode)}>
    {#if $themeStore.darkMode}
      <MenuEntry icon={SunIcon}>Theme</MenuEntry>
    {:else }
      <MenuEntry icon={MoonIcon}>Theme</MenuEntry>
    {/if}
  </div>



<style lang="scss">

</style>