<svelte:head>
  <title>Exportify Playlists</title>
</svelte:head>

<script lang="ts">
  import {page} from "$app/stores";
  import CardView from "../../../../lib/client/components/CardView.svelte";
  import {playlistStore} from "$lib/client/stores/library";
  import type {LibraryPlaylist} from "$lib/types/Playlist";
  import LibraryView from "../../../../lib/client/components/LibraryView.svelte";
  import type  {Card} from "$lib/types/Card";
  import type {LibraryItem} from "../../../../lib/types/Library";
  import {playlistToCard} from "$lib/types/Card";

  export let libraryItem: LibraryItem<LibraryPlaylist[]>
  $playlistStore = libraryItem
  let cards: Card[]
  $: cards = ($playlistStore.item || []).map(playlistToCard)
  let search = ""

</script>


<LibraryView title="Playlists" libItem={$playlistStore} bind:search>
  <CardView cards="{cards}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>

