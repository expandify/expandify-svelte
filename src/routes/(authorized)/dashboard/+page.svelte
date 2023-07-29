<script lang="js">
  import Button from "$lib/components/common/Button.svelte";
  import Metric from "$lib/components/dashboard/Metric.svelte";
  import WebPlayer from "$lib/components/dashboard/WebPlayer.svelte";
  import { dependencies } from "$lib/stores/dependencies";
  import { albums } from "$lib/stores/library/albums";
  import { artists } from "$lib/stores/library/artists";
  import { playlists } from "$lib/stores/library/playlists";
  import { tracks } from "$lib/stores/library/tracks";
  import { user } from "$lib/stores/library/user";


  //dependencies.setDependencies(true, true, true, true, true);
  //dependencies.noNeeds();
  dependencies.userNeeded();

</script>

<h1>Dashboard</h1>

<div class="tester">
  <Button text="Tester" type='elevated'></Button>
</div>


<div class="tiles">
  <Metric title={"Saved Albums"} metric={$albums.total} />
  <Metric title={"Followed Artists"} metric={$artists.total} />
  <Metric title={"Playlists"} metric={$playlists.total} />
  <Metric title={"Saved Tracks"} metric={$tracks.total} />
  {#if $user.user?.followers}
  <Metric title={"Your Followers"} metric={$user.user?.followers?.total} />  
  {/if}  
  <WebPlayer></WebPlayer>
</div>



<style lang="scss">

  .tester {    
    background-color: var(--background-elevated-highlight);
    width: 40rem;
    height: 30rem;
  }

  .tiles {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 2rem;
    justify-content: space-between;

  }


</style>