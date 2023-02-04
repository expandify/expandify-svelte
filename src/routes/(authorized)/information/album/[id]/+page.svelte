<script lang="ts">
	import { page } from "$app/stores";
	import AlbumImage from "$lib/components/images/AlbumImage.svelte";
	import AlbumTrackTable from "$lib/components/layout/AlbumTrackTable.svelte";
	import TrackTable from "$lib/components/layout/TrackTable.svelte";
	import LoadingDots from "$lib/components/loading/LoadingDots.svelte";
	import { formateDate, msToTime } from "$lib/helpers/converters";
	import { getAlbumWithTracks } from "$lib/spotify/api/albums";

</script>

{#await getAlbumWithTracks($page.params.id)}
  <LoadingDots message={"Loading Album"} />
{:then album} 

<div class="header">
  <AlbumImage album={album} borderRadius="0"/>
  <div class="header-infos">
    <span>Album</span>
    <h1 class="title">{album.name}</h1>

    <div class="bottom-infos">
      <span class="artists">{album.artists.map(a => a.name).join(", ")}</span>
      <span>·</span>
      <span class="release">{formateDate(album.release_date)}</span>
      <span>·</span>
      <span class="song-count">{album.tracks.length} songs</span>
      <span>·</span>
      <span class="playtime">{msToTime(album.tracks.reduce((d, a) => d + a.duration_ms, 0))}</span>
    </div>    
  </div>
</div>  
<AlbumTrackTable album={album}/>
{/await}




<style lang="scss">

  .header {
    height: 15rem;
    display: flex;
    flex-direction: row;
    gap: 2rem;
    margin-bottom: 2rem;

    .header-infos {
      display: flex;
      flex-direction: column;
      justify-content: flex-end;

      .title {
        font-size: 4rem;
        margin-top: 0;
        margin-bottom: 1rem;
      }

      .bottom-infos {
        display: flex;
        flex-direction: row;
        gap: 0.3rem;
        white-space: nowrap;
        
        .artists {
          font-weight: 500;
        }

        .playtime {
          color: var(--text-subdued);
        }
      }
    }
  }
</style>
