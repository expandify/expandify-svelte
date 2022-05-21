<script>
  import "../styles/app.scss";
  import Footer from "../lib/components/Footer.svelte";
  import SideBar from "../lib/components/SideBar.svelte";
  import {setTheme} from "../stores/theme.js";
  import {onMount} from 'svelte';

  onMount(() => setTheme());

</script>


<div class="footer-wrapper">
  <div class="sidebar-wrapper">
    <div>
      <SideBar/>
    </div>
    <div class="slot">
      <slot/>
    </div>
  </div>

  <Footer/>
</div>

<style lang="scss">

  .footer-wrapper {
    display: flex;
    flex-direction: column;
    min-height: 100vh;

    .sidebar-wrapper {
      display: flex;
      flex-direction: row;

      .slot {
        width: 100%;
      }
    }
  }

</style>