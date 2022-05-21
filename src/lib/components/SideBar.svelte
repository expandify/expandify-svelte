<script>
  import {LogOutIcon} from 'svelte-feather-icons'
  import ThemeChanger from "./ThemeChanger.svelte";
  import LibraryMenu from "./menu/LibraryMenu.svelte";
  import MenuEntry from "./menu/MenuEntry.svelte";
  import SpotifyButton from "./SpotifyButton.svelte";

  function logout() {
    const theme = localStorage.getItem("theme")
    localStorage.clear()
    localStorage.setItem("theme", theme)
  }
</script>

<aside class="sidebar">
  <a href="/" class="title">Exportify</a>
  <hr class="divider">
  <LibraryMenu/>
  <div class="top-bottom-split"></div>

  <ThemeChanger/>
  <hr class="divider">
  <div on:click={logout}>
    <MenuEntry href="/api/auth/logout" icon={LogOutIcon}>Sign Out</MenuEntry>
  </div>
</aside>

<style lang="scss">

  .sidebar {
    width: 15rem;
    min-height: 100vh;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    background-color: var(--bg-main-100);
    top: 0;
    padding: 1.5rem;
    position: sticky;

    .title {
      font-size: 2rem;
    }

    .top-bottom-split {
      margin-top: auto;
      color: unset;
      background-color: unset;
    }

    .divider {
      border-radius: 5rem;
      border-color: var(--accent);
      border-width: 0.2rem;
      border-style: solid;
      width: 100%;
      background-color: var(--accent);
      margin-top: 1rem;
      margin-bottom: 1rem;
    }

  }

</style>
