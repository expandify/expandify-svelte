<script lang="ts">
    import {albums} from "$lib/stores/library/albums";
    import {artists} from "$lib/stores/library/artists";
    import {playlists} from "$lib/stores/library/playlists";
    import {tracks} from "$lib/stores/library/tracks";
    import type {Album, Artist, Playlist, Track} from "$lib/types/spotify";
    import {dependencies} from "$lib/stores/dependencies";

    dependencies.setDependencies(true, true, true, true, true);

    let search: string = '';
    let delayedSearch = search;
    $: setTimeout(() => (delayedSearch = search), 500);

    let filteredPlaylists: Playlist[];
    let playlistsTracks: { [id: string]: Track[] } = {};
    $: $playlists, filteredPlaylists = searchPlaylists(delayedSearch);

    let filteredTracks: Track[];
    $: $tracks, filteredTracks = searchTracks(delayedSearch, $tracks.tracks);

    let filteredAlbums: Album[];
    let albumTracks: { [id: string]: Track[] } = {};
    $: $albums, filteredAlbums = searchAlbums(delayedSearch);


    let filteredArtists: Artist[];
    $: $artists, filteredArtists = searchArtists(delayedSearch);

    function searchArtists(filter: string) {
        filter = filter.toLowerCase();
        const filteredArtists = [];

        for (const artist of $artists.artists) {
            const name = artist.name.toLowerCase();

            if (name.indexOf(filter) !== -1) {
                filteredArtists.push(artist);
            }
        }
        return filteredArtists;
    }

    function searchTracks(filter: string, tracks: Track[]) {
        filter = filter.toLowerCase();
        const filteredTracks = [];

        for (const track of tracks) {
            const name = track.name.toLowerCase();
            const artists = track.artists
                .map((x) => x.name)
                .join(', ')
                .toLowerCase();

            // Will be null for TrackSimplified. Is ok, since it will be ignored
            const album = track.album?.name.toLowerCase();

            if (
                name.indexOf(filter) !== -1 ||
                artists.indexOf(filter) !== -1 ||
                (album && album.indexOf(filter) !== -1)
            ) {
                filteredTracks.push(track);
            }
        }
        return filteredTracks;
    }

    function searchPlaylists(filter: string) {
        filter = filter.toLowerCase();
        const filteredPlaylists = [];

        for (const playlist of $playlists.playlists) {
            const name = playlist.name.toLowerCase();

            const tracks = searchTracks(filter, playlist.tracks);
            playlistsTracks[playlist.id] = tracks;

            if (name.indexOf(filter) !== -1 || tracks.length > 0) {
                filteredPlaylists.push(playlist);
            }
        }
        return filteredPlaylists;
    }

    function searchAlbums(filter: string) {
        filter = filter.toLowerCase();
        const filterdAlbums = [];

        for (const album of $albums.albums) {
            const name = album.name.toLowerCase();

            const tracks = searchTracks(filter, album.tracks as Track[]);
            albumTracks[album.id] = tracks;

            if (name.indexOf(filter) !== -1 || tracks.length > 0) {
                filterdAlbums.push(album);
            }
        }
        return filterdAlbums;
    }
</script>

<p>Search for a song in your library.</p>

<input bind:value={search} placeholder="Find a song..."/>

<div class="playlists">
    {#if filteredPlaylists.length > 0}
        <h1>Playlists</h1>
    {/if}
    {#each filteredPlaylists as playlist}
        <h3>{playlist.name}</h3>
        {#each playlistsTracks[playlist.id] as track}
            <span>{track.name}</span>
        {/each}
    {/each}

    {#if filteredTracks.length > 0}
        <h1>Saved Tracks</h1>
    {/if}
    {#each filteredTracks as track}
        <span>{track.name}</span>
    {/each}

    {#if filteredAlbums.length > 0}
        <h1>Saved Albums</h1>
    {/if}
    {#each filteredAlbums as album}
        <h3>{album.name}</h3>
        {#each albumTracks[album.id] as track}
            <span>{track.name}</span>
        {/each}
    {/each}

    {#if filteredArtists.length > 0}
        <h1>Saved Tracks</h1>
    {/if}
    {#each filteredArtists as artist}
        <span>{artist.name}</span>
    {/each}
</div>

<style lang="scss">
  .playlists {
    display: flex;
    flex-direction: column;
  }
</style>
