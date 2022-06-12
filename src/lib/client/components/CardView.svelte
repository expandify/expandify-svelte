<script lang="ts">
  import CardComponent from "./Card.svelte";
  import type {Card} from "../../types/Card";


  export let hrefBasePath: string | null = null
  export let cards: Card[] = []
  export let search: string = ""


  let reactiveCards: Card[]
  $: reactiveCards = filterCards(search, cards.map(value => ({...value, href: `${hrefBasePath}/${value.id}`})))

  function filterCards(filter: string, toFilter: Card[]) {
    return toFilter.filter(value => value.title.toLowerCase().includes(filter.toLowerCase()));
  }

</script>


<div class="cards">
  {#each reactiveCards as card}
    <CardComponent card="{card}"/>
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


