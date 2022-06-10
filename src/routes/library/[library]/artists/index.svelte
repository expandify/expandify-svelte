<script>
  import {page} from "$app/stores";
  import CardView from "../../../../lib/client/components/elements/CardView.svelte";
  import {artistStore} from "../../../../lib/client/stores/library";
  import {imageSelector} from "../../../../lib/shared/helpers";

  export let items = []
  export let last_updated
  $artistStore.artists = items
  $: cards = parseArtists($artistStore.artists)

  function parseArtists(artists) {
    return artists.map(artist => ({
      title: artist.name,
      image: imageSelector(artist.images),
      id: artist.id
    }))
  }
</script>

<CardView title="Artists"
          cards="{cards}"
          state="{$artistStore.state}"
          hrefBasePath="{$page.url.href}"
          lastUpdated="{last_updated}"
/>

