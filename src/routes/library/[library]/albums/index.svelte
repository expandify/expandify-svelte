<script>
  import _ from "lodash";
  import CardView from "../../../../lib/components/elements/CardView.svelte";
  import {page} from "$app/stores";

  export let items = []

  let default_image = "/images/default.png"
  let cards = items.map(album => ({
    title: album.name,
    subtitle: artistsToString(album.artists),
    image: imageSelector(album.images),
    id: album.id
  }))


  function artistsToString(artists) {
    return artists.map(artist => artist.name).join()
  }

  function imageSelector(images) {
    if (!images || images.length === 0) {
      return default_image
    }
    return _.maxBy(images, ["height", "width"]).url
  }

</script>


<CardView title="Albums" gotoPath="{$page.url.href}" cards="{cards}"/>



