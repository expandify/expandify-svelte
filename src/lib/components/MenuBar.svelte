<script>
  import { onMount } from 'svelte'

  let showNav = false;

  function toggleNav() {
    showNav = !showNav
  }
</script>


<div>
  <nav class="navbar bg-base-100" aria-label="main navigation">
    <div class="navbar-brand">
      <a href="/" class="navbar-item is-size-3">
        Exportify
      </a>

      <button class="navbar-burger" on:click={toggleNav} class:is-active={showNav}
         aria-label="menu" aria-expanded="false">
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
        <span aria-hidden="true"></span>
      </button>
    </div>


    <select >
      <option value="light">Light</option>
      <option value="dark">Dark</option>
      <option value="night">Night</option>
    </select>

    <div id="navbarBasicExample" class="navbar-menu" class:is-active={showNav}>
      <div class="navbar-start"></div>
      <div class="navbar-end">
        <a class="navbar-item" href="/">Home</a>
        <a class="navbar-item" href="/">Features</a>
        <a class="navbar-item" href="/">FAQs</a>
        <a class="navbar-item" href="/">About</a>
      </div>
    </div>
  </nav>
</div>

<style lang="scss">

</style>
