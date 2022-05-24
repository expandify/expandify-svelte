<script>
  import {artistStore, STORE_STATUS} from "../../../stores/library.js";
  import Table from "../../../lib/client/components/elements/Table.svelte";
  import LoadingBar from "../../../lib/client/components/elements/LoadingBar.svelte";
  import {onMount} from "svelte";
  import {populateCursorStore} from "../../../lib/client/library/store.js";


  export let items = null

  let headers = ["Name", "Genres", "Popularity", "Followers"]
  let artists; // This is not necessary, but otherwise the IDE is not happy
  $: artists = $artistStore.items.map(artist => ({
    "Name": artist.name,
    "Genres": artist.genres.join(", "),
    "Popularity": artist.popularity,
    "Followers": artist.followers?.total
  }))

  onMount(() => {
    populateCursorStore(artistStore, "/library/artists/__data.json", {items: items}, (data) => data.items?.artists)
  })

</script>

{#if $artistStore.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Albums" max="{$artistStore.total}" current="{$artistStore.items.length}" status={$artistStore.status}/>
{:else}
  <Table headers={headers} items={artists}/>
{/if}


