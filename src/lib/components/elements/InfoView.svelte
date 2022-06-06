<script>
  export let infoStruct = {}

  $: items = Object.entries(infoStruct)
</script>

<div class="rows">
  {#each items as [key, value]}
    <div class="cols">
      <div class="key">{key}</div>
      <div class="value">{value}</div>
    </div>
  {/each}
</div>

<style lang="scss">

  .rows {
    display: flex;
    flex-direction: column;
    gap: 2rem;

    .cols {
      display: flex;
      flex-direction: row;

      .key {
        width: 20rem;
        color: var(--accent);
      }

      .value {
        overflow-wrap: break-word;
        width: 40rem;
      }
    }
  }
</style>