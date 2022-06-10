<script>
  import Card from "./Card.svelte";

  export let hrefBasePath = null
  export let cards = []
  export let search = ""
  $: reactiveCards = filterCards(search, cards)

  function filterCards(filter, toFilter) {
    return toFilter.filter(value => value.title.toLowerCase().includes(filter.toLowerCase()));
  }

</script>


<div class="cards">
  {#each reactiveCards as card}
    <Card title="{card.title}"
          subtitle="{card.subtitle || ''}"
          image="{card.image}"
          href="{`${hrefBasePath}/${card.id}`}"
    />
  {/each}
</div>


<style lang="scss">

  .cards {
    display: grid;
    grid-template-columns: repeat(auto-fill, 12rem);
    grid-gap: 2rem;
    justify-content: space-between;
  }

</style>


