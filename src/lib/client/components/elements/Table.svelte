<script>
  import _ from "lodash";
  import {ChevronDownIcon} from 'svelte-feather-icons'

  export let headers = []
  export let items = []
  export let sizes = []

  let sortHeader = headers[0]
  $: sortedItems = _.sortBy(items, [sortHeader])

</script>


<section class="table">
  <header class="row">
    {#each headers as header, i}
      <div class="col" style:width="{sizes[i]}" on:click={() => sortHeader = header}>{header}
        {#if header === sortHeader}
        <ChevronDownIcon/>
        {/if}
      </div>
    {/each}
  </header>
  {#each sortedItems as item}
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
      padding: 1rem;
      display: flex;
      min-height: 2rem;
      height: 2rem;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;

      .col {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
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

      .col {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
      }

      .col:hover {
        background-color: var(--bg-main-100);
        box-shadow: 0 0 0 1rem var(--bg-main-100);
        border-radius: 0.5rem;
        cursor: pointer;

      }
    }


  }
</style>