<script>
  import ThemeChanger from "../elements/ThemeChanger.svelte";
  import MenuList from "../menu/MenuList.svelte";
  import Divider from "../elements/Divider.svelte";
  import LogoutButton from "../elements/LogoutButton.svelte";

</script>

<aside class="sidebar">
  <a href="/" class="title">Exportify</a>
  <Divider/>
  <MenuList/>
  <div class="top-bottom-split"></div>

  <ThemeChanger/>
  <Divider/>
  <LogoutButton/>
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

  }

</style>
