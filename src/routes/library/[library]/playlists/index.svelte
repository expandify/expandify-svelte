<script lang="ts">
  import {page} from "$app/stores";
  import CardView from "../../../../lib/client/components/CardView.svelte";
  import {playlistStore} from "$lib/client/stores/library";
  import {imageSelector} from "$lib/shared/helpers";
  import type {LibraryPlaylist, Playlist} from "$lib/shared/types/Playlist";
  import LibraryView from "../../../../lib/client/components/LibraryView.svelte";
  import type  {Card} from "$lib/shared/types/Card";
  import {LibraryItem} from "../../../../lib/shared/types/Library";
  import {playlistToCard} from "$lib/shared/types/Card";

  export let libraryItem: LibraryItem<LibraryPlaylist[]>
  $playlistStore = libraryItem
  let cards: Card[]
  $: cards = $playlistStore.item.map(playlistToCard)
  let search = ""

</script>


<LibraryView title="Playlists" state="{$playlistStore.status}" lastUpdated="{$playlistStore.last_updated}" bind:search>
  <CardView cards="{cards}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>

