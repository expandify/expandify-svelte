<script>
	import NavBar from "$lib/components/NavBar.svelte";
	import { state } from "$lib/stores/state";
  
</script>

<div class="page">
  {#if $state.authenticated}
    <div class="navbar">
      <NavBar/>
    </div>
  {/if}
  <div class="content">
    <slot></slot>
  </div>
</div>

<style lang="scss">
  .page {
    display: flex;
    flex-direction: row; 

    .navbar {
      width: 10%;
    }

    .content {
      width: 100%;
    }
    
  }

  
</style>