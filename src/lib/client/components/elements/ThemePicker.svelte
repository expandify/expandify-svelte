<script lang="ts">
  import {themeStore, setDarkMode} from "../../stores/theme";
  import {SunIcon, MoonIcon} from 'svelte-feather-icons'
  import MenuEntry from "./IconLink.svelte";

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