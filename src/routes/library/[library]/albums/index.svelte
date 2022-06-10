<script>
  import CardView from "../../../../lib/client/components/elements/CardView.svelte";
  import {page} from "$app/stores";
  import {albumStore} from "../../../../lib/client/stores/library";
  import {imageSelector} from "../../../../lib/shared/helpers";
  import LibraryView from "../../../../lib/client/components/elements/LibraryView.svelte";

  export let items = []
  export let last_updated
  $albumStore.albums = items
  $: cards = parseAlbums($albumStore.albums)
  let search = ""

  function parseAlbums(albums) {
    return albums.map(album => ({
      title: album.name,
      subtitle: album.artists.map(artist => artist.name).join(),
      image:  imageSelector(album.images),
      id: album.id
    }))
  }

</script>

<LibraryView title="Albums" state="{$albumStore.state}" lastUpdated="{last_updated}" bind:search>
  <CardView></CardView>
</LibraryView>
<CardView title="Albums"
          cards="{cards}"
          state="{$albumStore.state}"
          hrefBasePath="{$page.url.href}"
          lastUpdated="{last_updated}"
          />







