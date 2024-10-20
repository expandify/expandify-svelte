<script lang="ts">
    import {dependencies} from "$lib/stores/dependencies";
    import {albums} from "$lib/stores/library/albums";
    import {artists} from "$lib/stores/library/artists";
    import {playlists} from "$lib/stores/library/playlists";
    import {tracks} from "$lib/stores/library/tracks";
    import {user} from "$lib/stores/library/user";
    import Loading from "../common/Loading.svelte";
    import {spotifyPersistence} from "$lib/services/spotify/spotify-persistance";
    import { Modal } from 'flowbite-svelte';

    $: anyNeeded =
        ($dependencies.albums && ($albums.loading || $albums.error)) ||
        ($dependencies.artists && ($artists.loading || $artists.error)) ||
        ($dependencies.playlists && ($playlists.loading || $playlists.error)) ||
        ($dependencies.tracks && ($tracks.loading || $tracks.error)) ||
        ($dependencies.user && ($user.loading || $user.error));
</script>

{#if anyNeeded}
    <Modal title="Loading" bind:open={anyNeeded} size="lg" dismissable="{false}" >
        {#if $dependencies.albums }
            <Loading
              title={"Albums"}
              current={$albums.albums.length}
              total={$albums.total}
              loading={$albums.loading}
              error={$albums.error !== null}
              on:retry={() => spotifyPersistence.reloadSavedAlbums()}/>
        {/if}

        {#if $dependencies.artists}
            <Loading
              title={"Artists"}
              current={$artists.artists.length}
              total={$artists.total}
              loading={$artists.loading}
              error={$artists.error !== null}
              on:retry={() => spotifyPersistence.reloadFollowedArtists()}
            />
        {/if}

        {#if $dependencies.playlists}
            <Loading
              title={"Playlists"}
              current={$playlists.playlists.length}
              total={$playlists.total}
              loading={$playlists.loading}
              error={$playlists.error !== null}
              on:retry={() => spotifyPersistence.reloadPlaylists()}/>
        {/if}

        {#if $dependencies.tracks}
            <Loading
              title={"Tracks"}
              current={$tracks.tracks.length}
              total={$tracks.total}
              loading={$tracks.loading}
              error={$tracks.error !== null}
              on:retry={() => spotifyPersistence.reloadSavedTracks()}/>
        {/if}

        {#if $dependencies.user}
            <Loading
              title={"User"}
              loading={$user.loading}
              error={$user.error !== null}
              on:retry={() => spotifyPersistence.reloadUser()}/>
        {/if}
    </Modal>
{/if}
