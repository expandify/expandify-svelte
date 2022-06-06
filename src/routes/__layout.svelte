<script>
  import "../styles/app.scss";
  import {onMount} from "svelte";
  import {initTheme} from "../stores/theme.js";
  import {page, session} from "$app/stores";
  import {config} from "../stores/config.js";
  import Footer from "../lib/components/page-elements/Footer.svelte";
  import MenuBar from "../lib/components/page-elements/MenuBar.svelte";


  config.update(value => ({...value, BASE_URL: $session.BASE_URL}))
  onMount(() => initTheme())
</script>

<div class="wrapper">
  <div class="fill-page">
    <MenuBar/>
    <div class="slot">
      <slot/>
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

      .slot {
        width: 100%;
        box-sizing: border-box;
      }
    }
  }


</style>