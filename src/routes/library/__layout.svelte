<script context="module">
  export async function load({session}) {
    if (!session.loggedIn) {
      return { redirect: "/", status: 302}
    }
    return { props: {} }
  }
</script>

<script>
  import SideBar from "../../lib/components/SideBar.svelte";

</script>


<div class="columns ">

  <div class="column is-one-fifth">
    <SideBar/>
  </div>

  <div class="column section">
    <slot/>
  </div>
</div>

<style lang="scss">

</style>
