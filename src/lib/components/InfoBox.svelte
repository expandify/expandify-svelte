<script>
  export let title;
  export let warning = "";
</script>

<div class="box has-background-grey-darker mb-0 m-5">
  <h2 class="title " class:mb-6={!warning}>{ title }</h2>
  {#if warning !== ""}
    <div class="has-text-danger is-size-6">{ warning }</div>
  {/if}
  <slot>
    <!-- the mdsvex content will be slotted in here -->
  </slot>
</div>

<style lang="scss">

  .box {
    width: 370px;

    height: 400px;
    overflow: hidden;
  }
</style>