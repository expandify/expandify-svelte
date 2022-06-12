<script lang="ts">
  import CardView from "../../../../lib/client/components/CardView.svelte";
  import {page} from "$app/stores";
  import {albumStore} from "$lib/client/stores/library";
  import LibraryView from "../../../../lib/client/components/LibraryView.svelte";
  import type {Card} from "$lib/shared/types/Card";
  import {LibraryItem} from "../../../../lib/shared/types/Library";
  import {albumToCard} from "$lib/shared/types/Card";
  import type {LibraryAlbum} from "../../../../lib/shared/types/Album";

  export let libraryItem: LibraryItem<LibraryAlbum[]>
  $albumStore = libraryItem
  let cards: Card[]
  $: cards = $albumStore.item.map(albumToCard)
  let search = ""

</script>

<LibraryView title="Albums" state="{$albumStore.status}" lastUpdated="{$albumStore.last_updated}" bind:search>
  <CardView cards="{cards}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>








