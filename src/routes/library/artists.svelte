<script>
  import {artists} from "../../stores/library/artists.js";
  import Table from "../../lib/client/components/elements/Table.svelte";
  import LoadingBar from "../../lib/client/components/elements/LoadingBar.svelte";
  import {STORE_STATUS} from "../../lib/client/library/store.js";

  let headers = ["Name", "Genres", "Popularity", "Followers"]
  let sizes = ["14rem", "20rem", "8rem", "8rem"]
  let items; // This is not necessary, but otherwise the IDE is not happy
  $: items = $artists.items.map(artist => ({
    "Name": artist.name,
    "Genres": artist.genres.join(", "),
    "Popularity": artist.popularity,
    "Followers": artist.followers?.total
  }))

</script>

{#if $artists.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Artists" max="{$artists.total}" current="{$artists.items.length}" status={STORE_STATUS.FETCHING}/>
{:else}
  <Table headers={headers} items={items} sizes="{sizes}"/>
{/if}


