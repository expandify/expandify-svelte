<script>
  import {page} from "$app/stores";
  import CardView from "../../../../lib/components/elements/CardView.svelte";
  import _ from "lodash";

  export let items

  let default_image = "/images/default.png"
  let cards = items.map(playlist => ({
    title: playlist.name,
    subtitle: playlist.owner.display_name,
    image: imageSelector(playlist.images),
    id: playlist.id
  }))

  function imageSelector(images) {
    if (!images || images.length === 0) {
      return default_image
    }
    return _.maxBy(images, ["height", "width"]).url
  }

</script>


<CardView title="Playlists" gotoPath="{$page.url.href}" cards="{cards}"/>

