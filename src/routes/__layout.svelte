<script lang="ts">
  import "../app.scss";
  import {onMount} from "svelte";
  import {initTheme} from "$lib/client/stores/theme";
  import Footer from "$lib/client/components/page-elements/Footer.svelte";
  import MenuBar from "$lib/client/components/page-elements/MenuBar.svelte";


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