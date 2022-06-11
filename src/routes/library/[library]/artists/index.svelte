<script lang="ts">
  import {page} from "$app/stores";
  import CardView from "$lib/client/components/elements/CardView.svelte";
  import {artistStore} from "$lib/client/stores/library";
  import {imageSelector} from "$lib/shared/helpers";
  import LibraryView from "$lib/client/components/elements/LibraryView.svelte";
  import type {Artist} from "$lib/shared/types/Artist";
  import type {Card} from "$lib/shared/types/Card";

  export let items: Artist[] = []
  export let last_updated: string
  $artistStore.artists = items
  let cards: Card[]
  $: cards = parseArtists($artistStore.artists)
  let search = ""

  function parseArtists(artists: Artist[]): Card[] {
    return artists.map(artist => ({
      title: artist.name,
      image: imageSelector(artist.images),
      id: artist.id
    }))
  }
</script>


<LibraryView title="Artists" state="{$artistStore.state}" lastUpdated="{last_updated}" bind:search>
  <CardView cards="{cards}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>

