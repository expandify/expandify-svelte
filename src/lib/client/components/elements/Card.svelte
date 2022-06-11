<script lang="ts">
  import {goto} from "$app/navigation";
  import type {Card} from "../../../shared/types/Card";

  export let card: Card

  function handleClick() {
    if (card.href) {
      goto(card.href)
    }
  }

</script>
<div class="card" on:click={handleClick}>
  <img src="{card.image}" class="image" alt="{card.title}">
  <div class="card-bottom">

    <div class="title">{card.title}</div>
    <div class="subtitle">{card.subtitle || ""}</div>
  </div>
</div>

<style lang="scss">


  .card {
    width: 10rem;
    height: 17rem;
    background-color: var(--bg-main-100);
    padding: 1rem;

    .image {
      width: 100%;
      object-fit: cover;
      aspect-ratio: 1/1;

      margin-bottom: 1rem;
    }

    .card-bottom {

      .title {
        font-size: 1.5rem;
        margin-bottom: 1rem;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }

      .subtitle {
        font-size: 1.2rem;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
    }
  }

  .card:hover {
    background-color: var(--bg-main-50);
    cursor: pointer;
  }
</style>