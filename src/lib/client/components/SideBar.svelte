<script>
  import IconLink from "./IconLink.svelte";
  import {FolderIcon, HomeIcon, UserIcon} from "svelte-feather-icons";
</script>

<aside class="sidebar">
  <ul class="menu-list">
    <li>
      <IconLink href="/" icon={HomeIcon}>Home</IconLink>
    </li>
    <li>
      <IconLink href="/library/user" icon={UserIcon}>User</IconLink>
    </li>
  </ul>
  <hr class="divider">
  <IconLink href="/library/current/albums" icon={FolderIcon}>Current library</IconLink>
  <span class="snapshot-title">Snapshots:</span>
  <div class="snapshots">

  </div>
</aside>

<style lang="scss">

  .sidebar {
    width: 15rem;
    height: 100vh;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    background-color: var(--bg-main-900);
    bottom: 0;
    top: 0;
    padding: 1.5rem;
    position: sticky;

    border-right: 0.1rem solid var(--bg-main-100);

    .divider {
      border-radius: 5rem;
      border-color: var(--bg-main-100);
      border-width: 0.05rem;
      height: 0.05rem;
      border-style: solid;
      width: 100%;
      background-color: var(--bg-main-100);
      margin-top: 1rem;
      margin-bottom: 1rem;
    }

    ul.menu-list {
      padding: 0;
      margin: 0;
      li {
        list-style-type: none;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        margin-bottom: 1rem;
      }
    }

    .snapshot-title {
      margin-top: 1rem;
      opacity: 30%;
    }
  }

</style>