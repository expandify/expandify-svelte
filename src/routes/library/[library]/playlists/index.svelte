<script>
  import {page} from "$app/stores";
  import CardView from "../../../../lib/client/components/elements/CardView.svelte";
  import _ from "lodash";
  import {playlistStore} from "../../../../lib/client/stores/library";


  export let items = []
  let default_image = "/images/default.png"
  $playlistStore.playlists = items

  $: cards = parsePlaylists($playlistStore.playlists)

  function parsePlaylists(playlists) {
    return playlists.map(playlist => ({
      title: playlist.name,
      subtitle: playlist.owner.display_name,
      image: imageSelector(playlist.images),
      id: playlist.id
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
  <CardView title="Playlists" gotoPath="{$page.url.href}" cards="{cards}"/>
{/key}
