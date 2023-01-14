<script lang="ts">
	import type { Album } from "$lib/classes/Album";
	import type { Artist } from "$lib/classes/Artist";
	import type { Playlist } from "$lib/classes/Playlist";
	import type { PlaylistTrack } from "$lib/classes/PlaylistTrack";
	import type { Track } from "$lib/classes/Track";
	import { albumCache, artistCache, playlistCache, trackCache } from "$lib/stores/cache";

  let search: string = ""
  let delayedSearch = search
  $: setTimeout(() => delayedSearch = search, 500)

  let playlists: Playlist[]
  let playlistsTracks: {[id: string]: PlaylistTrack[]} = {}
  $: playlists = searchPlaylists(delayedSearch)

  let tracks: Track[]
  $: tracks = searchTracks(delayedSearch)

  let albums: Album[]
  //$: albums = searchAlbums(delayedSearch)

  let artists: Artist[]
  //$: artists = searchArtists(delayedSearch)



  function searchTracks(filter: string) {    
    filter = filter.toLowerCase()
    const filteredTracks = []
    
    for (const track of $trackCache) {
      const name = track.name.toLowerCase();
      const artists = track.artists.map(x => x.name).join(", ").toLowerCase();
      const album = track.album?.name.toLowerCase();
      
      if (name.indexOf(filter) !== -1 ||
        artists.indexOf(filter) !== -1 ||
        (album && album.indexOf(filter) !== -1)) {
        filteredTracks.push(track)
      }
    }
    return filteredTracks
  }

  function searchPlaylists(filter: string) {
    filter = filter.toLowerCase()
    const filteredPlaylists = []

    for (const playlist of $playlistCache) {
      const name = playlist.name.toLowerCase();
      
      const tracks = searchPlaylistTracks(filter, playlist.tracks)
      playlistsTracks[playlist.id] = tracks;

      if (name.indexOf(filter) !== -1 || tracks.length > 0) {
        filteredPlaylists.push(playlist)
      }
    }
    return filteredPlaylists
  }

  function searchPlaylistTracks(filter: string, tracks: PlaylistTrack[]) {
    filter = filter.toLowerCase()
    const filteredTracks = []

    for (const track of tracks) {            
      if (track.track.type === "track") {
        
        const name = track.track.name.toLowerCase();
        const artists = track.track.artists.map(x => x.name).join(", ").toLowerCase();  
        const album = track.track.album?.name.toLowerCase();

        if (name.indexOf(filter) !== -1 ||
          artists.indexOf(filter) !== -1 ||
          (album && album?.indexOf(filter) !== -1)) {
          filteredTracks.push(track)
        }
      }
    }
    return filteredTracks
  }

</script>

<p>Search for a song in your library.</p>

<input bind:value={search} placeholder="Find a song...">


<div class="playlists">
  {#if playlists.length > 0}
  <h1>Playlists</h1>
  {/if}
  {#each playlists as playlist}
  <h3>{playlist.name}</h3>
  {#each playlistsTracks[playlist.id] as track}
  <span>{track.track.name}</span>  
  {/each}      
  {/each}

  {#if tracks.length > 0}
  <h1>Saved Tracks</h1>
  {/if}
  {#each tracks as track}
  <span>{track.name}</span>    
  {/each}
  
</div>  




<style lang="scss">
  .playlists {
    display: flex;
    flex-direction: column;
  }
</style>