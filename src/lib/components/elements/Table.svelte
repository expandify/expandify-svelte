<script>
  import _ from "lodash";
  import {ChevronDownIcon, ChevronUpIcon} from 'svelte-feather-icons'
  import {goto} from "$app/navigation";

  export let headers = []
  export let items = []
  export let gotoPath = null

  let sortHeader = headers[0]
  let sortedItems = items
  let sortOrder = null

  let reactiveItems
  $: reactiveItems = sortedItems

  function sort(header) {

    setHeader(header)

    if (sortOrder === null) {
      sortedItems = items
    } else {
      sortedItems = _.orderBy(items, sortHeader, sortOrder)
    }
  }

  function setHeader(header) {
    if (sortHeader !== header) {
      sortHeader = header
      sortOrder = "asc"
      return
    }
    if (sortOrder === "asc") {
      sortOrder = "desc"
      return;
    }
    if (sortOrder === "desc") {
      sortOrder = null
      return;
    }
    sortOrder = "asc"
  }

  function gotoId(id) {
    if (gotoPath !== null && id) {
      goto(`${gotoPath}/${id}`)
    }
  }

</script>

<table class="table">
  <tr class="header row">
    {#each headers as header, i}
      <th class="col" on:click={() => sort(header)}>
        <div class="title">
          {header}
          {#if header === sortHeader}
            {#if sortOrder === "asc" }
              <ChevronDownIcon/>
            {:else if sortOrder === "desc"}
              <ChevronUpIcon/>
            {/if}
          {/if}
        </div>
      </th>
    {/each}
  </tr>
  {#each reactiveItems as item}
    <tr class="row" on:click={() => gotoId(item.id)}>
      {#each headers as header, i}
        <td class="col">{item[header]}</td>
      {/each}
    </tr>
  {/each}
</table>
<style lang="scss">

  .table {
    border-spacing: 0;
    border-collapse: collapse;
    min-width: 100%;

    .row {
      .col {
        padding: 1rem;
        text-align: left;
        max-height: 3rem;

      }

      .col:first-child {
        border-radius: 0.5rem 0 0 0.5rem;
      }

      .col:last-child {
        border-radius: 0 0.5rem 0.5rem 0;
      }
    }

    .row:hover:not(.header) {
      background-color: var(--bg-main-100);
      border-radius: 0.5rem;
      cursor: pointer;
    }

    .header.row {
      text-transform: uppercase;
      border-bottom: 0.2rem solid var(--accent);
      height: 1.5rem;

      .col:hover {
        background-color: var(--bg-main-100);
        border-radius: 0.5rem;
        cursor: pointer;
      }

      .title {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        height: 2rem;
      }
    }
  }
</style>