<script lang="js">
    import Metric from "$lib/components/dashboard/Metric.svelte";
    import {dependencies} from "$lib/stores/dependencies";
    import {albums} from "$lib/stores/library/albums";
    import {artists} from "$lib/stores/library/artists";
    import {playlists} from "$lib/stores/library/playlists";
    import {tracks} from "$lib/stores/library/tracks";
    import {user} from "$lib/stores/library/user";






    //dependencies.setDependencies(true, true, true, true, true);
    //dependencies.noNeeds();
    dependencies.userNeeded();

</script>

<h1>Dashboard</h1>

<div class="flex flex-row flex-wrap gap-8">
    <Metric title={"Saved Albums"} metric={$albums.total}/>
    <Metric title={"Followed Artists"} metric={$artists.total}/>
    <Metric title={"Playlists"} metric={$playlists.total}/>
    <Metric title={"Saved Tracks"} metric={$tracks.total}/>
    {#if $user.user?.followers}
        <Metric title={"Your Followers"} metric={$user.user?.followers?.total}/>
    {/if}
</div>

