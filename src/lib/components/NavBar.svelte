<script lang="ts">
	import { reloadSavedAlbumsWithTracks } from "$lib/spotify/album";
	import { reloadFollowedArtists } from "$lib/spotify/artist";
	import { reloadUserPlaylistsWithTracks } from "$lib/spotify/playlist";
	import { realoadSavedTracks } from "$lib/spotify/track";



  function reloadLibrary() {
    reloadUserPlaylistsWithTracks();
    reloadSavedAlbumsWithTracks();
    realoadSavedTracks();
    reloadFollowedArtists();
  }
</script>

<aside class="container">
  <nav>
    <ul>
      <li><a href="/">Home</a></li>
      <li><p>Library</p></li>
      <li><button on:click={reloadLibrary}>Reload Library</button></li>
      <li><a href="/library/album">Albums</a></li>
      <li><a href="/library/artist">Artists</a></li>
      <li><a href="/library/playlist">Playlists</a></li>
      <li><a href="/library/track">Tracks</a></li>
      <li><p>Information</p></li>
      <li><a href="/information/album">Album Infos</a></li>
      <li><a href="/information/artist">Artist Infos</a></li>
      <li><a href="/information/track">Track Infos</a></li>
      <li><p>Tools</p></li>
      <li><a href="/tools/song-finder">Song Finder</a></li>
      <li><a href="/tools/backup">Backup</a></li>
    </ul>
  </nav>
</aside>

<style lang="scss">


</style>