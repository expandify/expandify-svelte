<script>
  import {page} from "$app/stores";
  import CardView from "../../../../lib/components/elements/CardView.svelte";
  import _ from "lodash";

  export let items = []

  let default_image = "/images/default.png"
  let cards = items.map(artist => ({
    title: artist.name,
    image: imageSelector(artist.images),
    id: artist.id
  }))

  function imageSelector(images) {
    if (!images || images.length === 0) {
      return default_image
    }
    return _.maxBy(images, ["height", "width"]).url
  }
</script>

<CardView title="Artists" gotoPath="{$page.url.href}" cards="{cards}"/>



