<script>
  import { GithubIcon } from 'svelte-feather-icons'
</script>


<footer class="footer">
  <div>&copy; Copyright Till Wittenburg</div>

  <a href="https://github.com/systeno" class="icon-link">
    <GithubIcon class="icon"/>
    GitHub
  </a>
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

    .icon-link {
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      align-items: center;

      :global(.icon) {
        stroke-width: 3;
        width: 1.2rem;
        height: 1.2rem;
        margin-right: 0.3rem;
      }
    }
  }
</style>