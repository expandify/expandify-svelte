<script>
  import _ from "lodash";
  import CardView from "../../../../lib/client/components/elements/CardView.svelte";
  import {page} from "$app/stores";
  import {albumStore} from "../../../../lib/client/stores/library";

  export let items = []
  let default_image = "/images/default.png"
  $albumStore.albums = items

  $: cards = parseAlbums($albumStore.albums)

  function parseAlbums(albums) {
    return albums.map(album => ({
      title: album.name,
      subtitle: artistsToString(album.artists),
      image: imageSelector(album.images),
      id: album.id
    }))
  }

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

{#key cards}
  <CardView title="Albums" gotoPath="{$page.url.href}" cards="{cards}"/>
{/key}




