<script lang="ts">
	import type { Playlist } from "$lib/classes/Playlist";
	import { msToTime } from "$lib/helpers/converters";

  export let playlists: Playlist[];

  function albumPodcastTitle(playlist: Playlist) {
    const types = [...new Set(playlist.tracks.map(t => t.type))];

    if (types.includes("track") && types.includes("episode")) {
      return "Album Or Podcast";
    }

    if (types.includes("track")) {
      return "Album";
    }

    if (types.includes("episode")) {
      return "Podcast";
    }

    return "Album";
  }

</script>

<div class="playlists">
	{#each playlists as playlist}

  <details>
    <!-- svelte-ignore a11y-no-redundant-roles -->
    <summary >{playlist.name}</summary>
    
    <table>
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Title</th>
          <th scope="col">{albumPodcastTitle(playlist)}</th>
          <th scope="col">Date Added</th>
          <th scope="col">Duration</th>
        </tr>
      </thead>
      <tbody>
        
        {#each playlist.tracks as track, i}
        <tr>                          
          <th scope="row">{i + 1}</th>
          <td class="title-group">
              <div class="title">{track.name}</div>
              <div class="artists">{track.artists.map(a => a.name).join(", ")}</div>
          </td>
          <td>{track.album?.name}</td>          
          <td>{track?.added_at && new Date(track?.added_at).toDateString()}</td>
          <td>{msToTime(track.duration_ms)}</td>
        </tr>
		    {/each}      
      </tbody>
    </table>
    
  </details>


		
	{/each}
</div>



<style lang="scss">
  .title-group {
    display: flex;
    flex-direction: column;

    .title {
      font-weight: 600;
      color: var(--contrast);
    }
  }
</style>