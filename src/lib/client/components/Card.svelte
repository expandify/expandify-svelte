<script lang="ts">
  import type { Card } from "$lib/types/Card";
  import ComparingDiv from "./ComparingDiv.svelte";

  export let card: Card;

</script>

<a class="card" href={card.href || ""}>
  <img src={card.image} class="image" alt={card.title} />
  <ComparingDiv libraryId={card.libraryId}>
    <div class="card-bottom">
      <div class="title">{card.title}</div>
      <div class="subtitle">{card.subtitle || ""}</div>
    </div>
  </ComparingDiv>
</a>

<style lang="scss">


  .card {
    width: 10rem;
    height: 14rem;
    padding: 1rem;
    border-radius: 0.5rem;
    border: 0.1rem solid rgba(0, 0, 0, 0);

    .image {
      width: 100%;
      object-fit: cover;
      aspect-ratio: 1/1;
      border-radius: 0.5rem;
      margin-bottom: 0.7rem;
    }

    .card-bottom {
      .title {
        font-size: 1.2rem;
        margin-bottom: 0.7rem;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }

      .subtitle {
        font-size: 0.8rem;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
    }
  }

  .card:hover {
    border: 0.1rem solid var(--accent);
    cursor: pointer;
    color: unset;
  }
</style>
