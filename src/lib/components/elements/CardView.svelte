<script>
  import AlbumCard from "../../../lib/components/elements/Card.svelte";
  import {goto} from "$app/navigation";
  import {SearchIcon} from "svelte-feather-icons";

  export let gotoPath = null
  export let cards = []
  export let title

  let search = ""
  let reactiveCards
  $: reactiveCards = filterCards(search)

  function filterCards(filter) {
    return cards.filter(value => value.title.toLowerCase().includes(filter.toLowerCase()));
  }

  function handleClick(id) {
    if (gotoPath !== null && id) {
      goto(`${gotoPath}/${id}`)
    }
  }

</script>


<div class="top">
  <h1>{title}</h1>
  <div class="search">
    <SearchIcon class="icon"/>
    <input bind:value={search} placeholder="Search in {title.toLowerCase()}">
  </div>

</div>

<div class="cards">
  {#each reactiveCards as card}
    <div on:click={() => handleClick(card.id)}>
      <AlbumCard title="{card.title}"
                 subtitle="{card.subtitle || ''}"
                 image="{card.image}"
                 id="{card.id}"
      />
    </div>
  {/each}
</div>

<style lang="scss">

  .top {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    .search {
      background-color: var(--bg-main-100);
      display: flex;
      flex-direction: row;
      align-items: center;
      height: 2rem;
      border-radius: 0.2rem;
      padding: 0.2rem;

      input {
        box-sizing: border-box;
        border: none;
        background-color: var(--bg-main-100);
        color: var(--text-700);
        margin-left: 0.4rem;
      }
      input:focus {
        outline: none;
      }
    }
  }

  .cards {
    display: grid;
    grid-template-columns: repeat(auto-fill, 12rem);
    grid-gap: 2rem;
    justify-content: space-between;
  }

</style>


