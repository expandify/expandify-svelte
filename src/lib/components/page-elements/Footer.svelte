<script>
  import { GithubIcon } from 'svelte-feather-icons'
  import MenuEntry from "../elements/MenuEntry.svelte";
</script>


<footer class="footer">
  <div>&copy; Copyright Till Wittenburg</div>

  <MenuEntry href="https://github.com/systeno" icon={GithubIcon}>
    GitHub
  </MenuEntry>
</footer>


<style lang="scss">

  .footer {
    height: 6rem;
    padding: 1rem;
    background: var(--bg-main-100);
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    :global(.icon) {
      margin-right: 0.5rem;
    }
  }
</style>