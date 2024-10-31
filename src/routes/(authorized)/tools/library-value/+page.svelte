<script lang="ts">
    import Button from "$lib/components/common/Button.svelte";
    import Loading from "$lib/components/common/Loading.svelte";
    import {calculateLibraryValue} from "./value-calculator";

     
    let savedAlbums = $state(false);
     
    let ownPlaylists = $state(false);
     
    let followedPlaylists = $state(false);
     
    let savedTracks = $state(false);

     
    let calculating = $state(false);
     
    let libraryValue = $state<number | null>(null);

    async function calcLibrary() {
        calculating = true;
        libraryValue = await calculateLibraryValue(savedAlbums, ownPlaylists, followedPlaylists, savedTracks);
        calculating = false;
    }

</script>

<span>Select the library parts you want to calculate.</span>
<div class="selection">
    <label>
        <input type=checkbox bind:checked={savedAlbums}>
        <span>Saved Albums</span>
    </label>
    <label>
        <input type=checkbox bind:checked={ownPlaylists}>
        <span>Created Playlists</span>
    </label>
    <label>
        <input type=checkbox bind:checked={followedPlaylists}>
        <span>Followed Playlists</span>
    </label>
    <label>
        <input type=checkbox bind:checked={savedTracks}>
        <span>Saved Tracks</span>
    </label>
</div>

{#if savedAlbums || ownPlaylists || followedPlaylists || savedTracks}
    <Button click={calcLibrary} text="Calculate Library Value" type="elevated"/>
{/if}


{#if calculating}
    <Loading title={"Calculating"}/>
{:else if libraryValue}
    <h2>Library value is {libraryValue}$</h2>
{/if}


<style lang="scss">
  .selection {
    display: flex;
    flex-direction: column;
  }
</style>
