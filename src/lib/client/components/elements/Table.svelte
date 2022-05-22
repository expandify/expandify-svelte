<script>
  export let headers = []
  export let items = []
  export let sizes = []
</script>


<section class="table">
  <header class="row">
    {#each headers as header, i}
      <div class="col" style:width="{sizes[i]}">{header}</div>
    {/each}
  </header>
  {#each items as item}
    <div class="row">
      {#each headers as header, i}
        <div class="col" style:width="{sizes[i]}">{item[header]}</div>
      {/each}
    </div>
  {/each}
</section>

<style lang="scss">

  .table {

    .row {
      padding-left: 1rem;
      padding-right: 1rem;
      display: flex;
      height: 3rem;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      .col {
        overflow-wrap: break-word;
        padding-right: 0.5rem;
      }
    }

    .row:hover:not(header) {
      background-color: var(--bg-main-100);
      border-radius: 0.5rem;
      cursor: pointer;
    }

    header.row {
      text-transform: uppercase;
      border-bottom: 0.2rem solid var(--accent);
      margin-bottom: 1.5rem;
      height: 1.5rem;
    }


  }
</style>