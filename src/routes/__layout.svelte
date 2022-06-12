<script lang="ts">
  import "../app.scss";
  import {onMount} from "svelte";
  import {initTheme} from "$lib/client/stores/theme";
  import Footer from "../lib/client/components/Footer.svelte";
  import Header from "../lib/client/components/Header.svelte";
  import SideBar from "../lib/client/components/SideBar.svelte";
  import {session} from "$app/stores";


  onMount(() => initTheme())
</script>

<div class="wrapper">
  <div class="fill-page">
    <Header/>
    <div class="row">
      {#if $session.loggedIn}
        <div class="sidebar"><SideBar/></div>
      {/if}
      <div class="slot"><slot/></div>
    </div>
  </div>
  <Footer/>
</div>
<style lang="scss">

  .wrapper {
    display: flex;
    flex-direction: column;
    .fill-page {
      min-height: 100vh;
      width: 100%;
      .row {
        display: flex;
        flex-direction: row;
        width: 100%;
        justify-content: flex-start;
        .slot {
          min-width: 0;
          width: 100%;
          box-sizing: border-box;
        }
      }
    }
  }


</style>