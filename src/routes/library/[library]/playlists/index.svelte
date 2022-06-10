<script lang="ts">
  import {page} from "$app/stores";
  import CardView from "../../../../lib/client/components/elements/CardView.svelte";
  import {playlistStore} from "../../../../lib/client/stores/library";
  import {imageSelector} from "../../../../lib/shared/helpers";
  import {Playlist} from "../../../../lib/shared/classes/Playlist";

  export let items = []
  export let last_updated
  $playlistStore.playlists = items
  $: cards = parsePlaylists($playlistStore.playlists)

  function parsePlaylists(playlists: Playlist[]) {
    return playlists.map(playlist => ({
      title: playlist.name,
      subtitle: playlist.owner,
      image: imageSelector(playlist.images),
      id: playlist.id
    }))
  }

</script>

<CardView title="Playlists"
          cards="{cards}"
          state="{$playlistStore.state}"
          hrefBasePath="{$page.url.href}"
          lastUpdated="{last_updated}"
/>
