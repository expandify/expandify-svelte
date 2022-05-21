<script>
  export let title;
  export let warning = "";
</script>

<div class="card">
  <h2 class="title" class:no-warning={!warning}>{ title }</h2>

  {#if warning !== ""}
  <div class="warning">{ warning }</div>
  {/if}

  <div class="content">
    <slot />
  </div>

</div>

<style lang="scss">

  .card {
    width: 17rem;
    height: 20rem;
    overflow: hidden;
    padding: 1rem;
    border-radius: 1rem;
    background-color: var(--bg-main-100);
    box-shadow: 0 0 1rem 0.1rem var(--bg-main-100);
    margin: 1rem;

    .title {
      margin-bottom: 0.5rem;
    }

    .title.no-warning {

      margin-bottom: 0.5rem + 1.5rem;
    }

    .warning {
      color: var(--accent);
      height: 1.5rem;
    }
  }


</style>