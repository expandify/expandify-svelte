<script lang="ts">
    import {dependencies} from "$lib/stores/dependencies";
    import {albums} from "$lib/stores/library/albums";
    import {artists} from "$lib/stores/library/artists";
    import {playlists} from "$lib/stores/library/playlists";
    import {tracks} from "$lib/stores/library/tracks";
    import {user} from "$lib/stores/library/user";
    import Loading from "../common/Loading.svelte";
    import {spotifyPersistence} from "$lib/services/spotify/spotify-persistance";
    import {
        AlertDialog, AlertDialogAction, AlertDialogCancel,
        AlertDialogContent, AlertDialogDescription, AlertDialogFooter,
        AlertDialogHeader, AlertDialogTitle,
    } from '$lib/components/ui/alert-dialog/index';


    let forceClose = $state(false)
    let anyNeeded = $state(false);
    let loading = $state(true);
    $effect(() => {
        forceClose = false;
        anyNeeded =
            ($dependencies.albums && ($albums.loading || !!$albums.error)) ||
            ($dependencies.artists && ($artists.loading || !!$artists.error)) ||
            ($dependencies.playlists && ($playlists.loading || !!$playlists.error)) ||
            ($dependencies.tracks && ($tracks.loading || !!$tracks.error)) ||
            ($dependencies.user && ($user.loading || !!$user.error));

        loading = ($dependencies.albums && $albums.loading) ||
          ($dependencies.artists && $artists.loading) ||
          ($dependencies.playlists && $playlists.loading) ||
          ($dependencies.tracks && $tracks.loading) ||
          ($dependencies.user && $user.loading);
    });


    function retry() {
        if ($dependencies.albums && $albums.error) {
            spotifyPersistence.reloadSavedAlbums()
        }
        if ($dependencies.artists && $artists.error) {
            spotifyPersistence.reloadFollowedArtists()
        }
        if ($dependencies.playlists && $playlists.error) {
            spotifyPersistence.reloadPlaylists()
        }
        if ($dependencies.tracks && $tracks.error) {
            spotifyPersistence.reloadSavedTracks()
        }
        if ($dependencies.user && $user.error) {
            spotifyPersistence.reloadUser()
        }
    }
</script>

<AlertDialog open={!forceClose && anyNeeded} controlledOpen={true}>
    <AlertDialogContent>
        <AlertDialogHeader>
            <AlertDialogTitle>Loading Library Data</AlertDialogTitle>
            <AlertDialogDescription>
                {#if $dependencies.albums}
                    <Loading
                      title={"Albums"}
                      current={$albums.albums.length}
                      total={$albums.total}
                      loading={$albums.loading}
                      error={$albums.error !== null}
                      retry={() => spotifyPersistence.reloadSavedAlbums()}/>
                {/if}

                {#if $dependencies.artists}
                    <Loading
                      title={"Artists"}
                      current={$artists.artists.length}
                      total={$artists.total}
                      loading={$artists.loading}
                      error={$artists.error !== null}
                      retry={() => spotifyPersistence.reloadFollowedArtists()}
                    />
                {/if}

                {#if $dependencies.playlists}
                    <Loading
                      title={"Playlists"}
                      current={$playlists.playlists.length}
                      total={$playlists.total}
                      loading={$playlists.loading}
                      error={$playlists.error !== null}
                      retry={() => spotifyPersistence.reloadPlaylists()}/>
                {/if}

                {#if $dependencies.tracks}
                    <Loading
                      title={"Tracks"}
                      current={$tracks.tracks.length}
                      total={$tracks.total}
                      loading={$tracks.loading}
                      error={$tracks.error !== null}
                      retry={() => spotifyPersistence.reloadSavedTracks()}/>
                {/if}

                {#if $dependencies.user}
                    <Loading
                      title={"User"}
                      loading={$user.loading}
                      error={$user.error !== null}
                      retry={() => spotifyPersistence.reloadUser()}/>
                {/if}
            </AlertDialogDescription>
        </AlertDialogHeader>
        <div class:hidden={loading}>
        <AlertDialogFooter>
            <AlertDialogCancel onclick={() => forceClose = true}>Close</AlertDialogCancel>
            <AlertDialogAction onclick={retry}>Retry</AlertDialogAction>
        </AlertDialogFooter>
        </div>
    </AlertDialogContent>
</AlertDialog>