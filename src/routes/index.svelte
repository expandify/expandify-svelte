<script>
  import {session} from "$app/stores";
  import SpotifyButton from "../lib/client/components/elements/SpotifyButton.svelte";
  import FeatureBar from "../lib/client/components/page-elements/FeatureBar.svelte";

  const features = [
    {
      "title": "Login with Spotify",
      "content": `Login with your Spotify account, to see your library.
                  Exportify will show you your: Playlists, Albums, Artists and Songs.
                  Exportify cannot delete or create anything. It will only read your existing data.`
    },
    {
      "title": "Download your library",
      "content": `Exportify gives you the ability to download your current library state.
                  Since this app is still in early development, the format of the downloaded data may change over time.`
    },
    {
      "title": "Backup your library",
      "warning": "Warning: experimental feature",
      "content": `Exportify also gives you the ability to safe your library online.
                  It is connected to your Spotify ID, so you dont need an additional account.
                  Do not rely on this yet. This is feature is not ready for serious use at this time.`
    },
    {
      "title": "Lorem Ipsum",
      "content": `Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras iaculis blandit facilisis.
                  Vestibulum a feugiat diam, eget egestas enim.
                  Proin varius neque quis orci gravida efficitur nec at libero.
                  Ut ut odio eu est vestibulum ullamcorper sed eu mauris.
                  Sed ac dapibus diam. Quisque congue pellentesque pellentesque.
                  Cras blandit, est id aliquet vulputate, erat est euismod massa, nec hendrerit neque neque in magna.
                  Nam non felis dui. Phasellus molestie turpis pharetra venenatis imperdiet. Suspendisse potenti.
                  In dignissim odio suscipit, euismod purus vel, laoreet dui.
                  Vivamus id nisl mi. Sed ac consequat lacus, nec euismod sem.
                  Vivamus scelerisque felis sed efficitur ornare.
                  Aliquam auctor aliquam ipsum quis lobortis.
                  Fusce massa nibh, sodales sollicitudin enim ac, tincidunt sollicitudin est.
                  Ut diam eros, imperdiet sollicitudin vestibulum quis, ornare eget tortor.
                  Pellentesque facilisis nisi vitae purus convallis, in volutpat elit cursus.
                  Morbi egestas tempor quam, ut dignissim mauris posuere nec.
                  Vivamus ultrices mi ut consequat porta. Duis turpis leo, ultricies at pretium nec, efficitur ut est.
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras iaculis blandit facilisis.
                  Vestibulum a feugiat diam, eget egestas enim.
                  Proin varius neque quis orci gravida efficitur nec at libero.
                  Ut ut odio eu est vestibulum ullamcorper sed eu mauris.
                  Sed ac dapibus diam. Quisque congue pellentesque pellentesque.
                  Cras blandit, est id aliquet vulputate, erat est euismod massa, nec hendrerit neque neque in magna.
                  Nam non felis dui. Phasellus molestie turpis pharetra venenatis imperdiet. Suspendisse potenti.
                  In dignissim odio suscipit, euismod purus vel, laoreet dui. Vivamus id nisl mi.
                  Sed ac consequat lacus, nec euismod sem. Vivamus scelerisque felis sed efficitur ornare.
                  Aliquam auctor aliquam ipsum quis lobortis.
                  Fusce massa nibh, sodales sollicitudin enim ac, tincidunt sollicitudin est.
                  Ut diam eros, imperdiet sollicitudin vestibulum quis, ornare eget tortor.
                  Pellentesque facilisis nisi vitae purus convallis, in volutpat elit cursus.
                  Morbi egestas tempor quam, ut dignissim mauris posuere nec.
                  ivamus ultrices mi ut consequat porta. Duis turpis leo, ultricies at pretium nec, efficitur ut est.
                  Pellentesque facilisis nisi vitae purus convallis, in volutpat elit cursus.
                  Morbi egestas tempor quam, ut dignissim mauris posuere nec.
                  ivamus ultrices mi ut consequat porta. Duis turpis leo, ultricies at pretium nec, efficitur ut est.
                  Pellentesque facilisis nisi vitae purus convallis, in volutpat elit cursus.
                  Morbi egestas tempor quam, ut dignissim mauris posuere nec.
                  ivamus ultrices mi ut consequat porta. Duis turpis leo, ultricies at pretium nec, efficitur ut est.`
    }

  ]

</script>

<div class="index">
  <div class="index-top">
    <div class="title">Export and back up your Spotify library.</div>
    {#if !$session.loggedIn}
      <SpotifyButton goto="/auth/login">Login With Spotify</SpotifyButton>
    {:else}
      <p class="logged-in">You are already Logged In</p>
      <SpotifyButton goto="/library">Go To Library</SpotifyButton>
    {/if}
  </div>

  <hr class="divider">
  <h2>Features</h2>
  <FeatureBar features={features}/>
</div>



<style lang="scss">

  .index {
    padding: 2rem;

    .index-top {
      height: 40rem;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      .title {
        font-size: 1.8rem;
        margin-bottom: 1rem;
      }

      .logged-in {
        color: var(--accent);
      }
    }
    .divider {
      border-radius: 5rem;
      border-color: var(--accent);
      border-width: 0.2rem;
      border-style: solid;
      width: 100%;
      background-color: var(--accent);
      margin-top: 1rem;
      margin-bottom: 1rem;
    }
  }


</style>