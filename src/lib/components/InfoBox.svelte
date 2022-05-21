<script>
  export let title;
  export let warning = "";
</script>

<div class="card w-96 h-96 bg-base-300 shadow-w1 text-clip">
  <div class="card-body">
    <h2 class="card-title" class:mb-6={!warning}>{ title }</h2>
  {#if warning !== ""}
    <div class="text-warning">{ warning }</div>
  {/if}
  <slot>
    
  </slot>
  </div>
  
</div>

<style lang="scss">

  .box {
    width: 370px;

    height: 400px;
    overflow: hidden;
  }
</style>