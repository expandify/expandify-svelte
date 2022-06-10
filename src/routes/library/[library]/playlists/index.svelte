<script lang="ts">
  import {page} from "$app/stores";
  import CardView from "../../../../lib/client/components/elements/CardView.svelte";
  import {playlistStore} from "../../../../lib/client/stores/library";
  import {imageSelector} from "../../../../lib/shared/helpers";
  import {Playlist} from "../../../../lib/shared/classes/Playlist";
  import LibraryView from "../../../../lib/client/components/elements/LibraryView.svelte";

  export let items = []
  export let last_updated
  $playlistStore.playlists = items
  $: cards = parsePlaylists($playlistStore.playlists)
  let search = ""

  function parsePlaylists(playlists: Playlist[]) {
    return playlists.map(playlist => ({
      title: playlist.name,
      subtitle: playlist.owner,
      image: imageSelector(playlist.images),
      id: playlist.id
    }))
  }

</script>


<LibraryView title="Playlists" state="{$playlistStore.state}" lastUpdated="{last_updated}" bind:search>
  <CardView cards="{cards}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>

