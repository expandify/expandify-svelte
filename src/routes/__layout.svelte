<script>
  import "../styles/app.scss";
  import {onMount} from "svelte";
  import {initTheme} from "../stores/theme.js";
  import {navigating, session} from "$app/stores";
  import Footer from "../lib/client/components/page-elements/Footer.svelte";
  import SideBar from "../lib/client/components/page-elements/SideBar.svelte";
  import PageLoader from "../lib/client/components/elements/PageLoader.svelte";

  onMount(() => initTheme())
</script>

  <div class="footer-wrapper">
    <div class="sidebar-wrapper">
      {#if $session.loggedIn}
        <div>
          <SideBar/>
        </div>
      {/if}
      <div class="slot">
        {#if $navigating}
          <PageLoader />
        {:else}
          <slot/>
        {/if}
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
        min-height: 100vh;
        width: 100%;
        padding: 2rem;
        box-sizing: border-box;

      }
    }
  }

</style>