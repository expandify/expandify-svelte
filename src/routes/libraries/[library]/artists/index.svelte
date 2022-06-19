<svelte:head>
  <title>Exportify Artists</title>
</svelte:head>

<script lang="ts">
  import {page} from "$app/stores";
  import CardView from "$lib/client/components/CardView.svelte";
  import {artistStore} from "$lib/client/stores/library";
  import LibraryView from "$lib/client/components/LibraryView.svelte";
  import type {LibraryArtist} from "$lib/types/Artist";
  import type {Card} from "$lib/types/Card";
  import type {LibraryItem} from "$lib/types/Library";
  import {artistToCard} from "$lib/types/Card";

  export let libraryItem: LibraryItem<LibraryArtist[]>
  $artistStore = libraryItem
  let cards: Card[]
  $: cards = ($artistStore.item || []).map(artistToCard)
  let search = ""
</script>


<LibraryView title="Artists" libItem={$artistStore} bind:search>
  <CardView cards="{cards}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>

