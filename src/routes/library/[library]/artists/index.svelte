<script>
  import {page} from "$app/stores";
  import CardView from "../../../../lib/client/components/elements/CardView.svelte";
  import _ from "lodash";
  import {artistStore} from "../../../../lib/client/stores/library";

  export let items = []
  let default_image = "/images/default.png"
  $artistStore.artists = items

  $: cards = parseArtists($artistStore.artists)

  function parseArtists(artists) {
    return artists.map(artist => ({
      title: artist.name,
      image: imageSelector(artist.images),
      id: artist.id
    }))
  }

  function imageSelector(images) {
    if (!images || images.length === 0) {
      return default_image
    }
    return _.maxBy(images, ["height", "width"]).url
  }
</script>


{#key cards}
  <CardView title="Artists" gotoPath="{$page.url.href}" cards="{cards}"/>
{/key}


