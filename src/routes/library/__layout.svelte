<script>
  import SideBar from "../../lib/components/SideBar.svelte";

</script>


<div class="is-flex">

  <div class="is-flex-shrink-1">
    <SideBar/>
  </div>

  <div class="is-flex-grow-1 section">
    <slot/>
  </div>
</div>

<style lang="scss">

</style>
