<script lang="ts">
  import {page} from "$app/stores";
  import CardView from "$lib/client/components/elements/CardView.svelte";
  import {playlistStore} from "$lib/client/stores/library";
  import {imageSelector} from "$lib/shared/helpers";
  import type {Playlist} from "$lib/shared/types/Playlist";
  import LibraryView from "$lib/client/components/elements/LibraryView.svelte";
  import type  {Card} from "$lib/shared/types/Card";

  export let items: Playlist[] = []
  export let last_updated: string
  $playlistStore.playlists = items
  let cards: Card[]
  $: cards = parsePlaylists($playlistStore.playlists)
  let search = ""

  function parsePlaylists(playlists: Playlist[]): Card[] {
    return playlists.map(playlist => ({
      title: playlist.name,
      subtitle: playlist.owner.name,
      image: imageSelector(playlist.images),
      id: playlist.id
    }))
  }

</script>


<LibraryView title="Playlists" state="{$playlistStore.state}" lastUpdated="{last_updated}" bind:search>
  <CardView cards="{cards}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>

