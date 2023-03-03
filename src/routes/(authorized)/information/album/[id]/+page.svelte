<script lang="ts">
	import { page } from "$app/stores";
	import Loading from "$lib/components/common/Loading.svelte";
	import { formateDate, msToTime } from "$lib/utils/converter/date-time";
	import { Spotify } from "$lib/data/spotify";
  import ImageWithFallback from "$lib/components/common/ImageWithFallback.svelte";
  import TrackRow from "$lib/components/layout/table/TrackRow.svelte";


</script>

{#await  Spotify.Album.loadAndGet($page.params.id)}
  <Loading title={"Album"} />
{:then album} 
<div class="header">
  <ImageWithFallback type={album} borderRadius="0"/>
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
<TrackRow track={null} showImage={false}></TrackRow>  
{#each album.tracks as track}
  <TrackRow track={track} showImage={false}></TrackRow>  
{/each} 

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
