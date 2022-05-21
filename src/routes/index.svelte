<script>
  import MenuBar from "$lib/components/MenuBar.svelte"
  import InfoBoxes from "$lib/../assets/info-boxes.json"
  import InfoBox from "$lib/components/InfoBox.svelte";
  import SpotifyButton from "../lib/components/SpotifyButton.svelte";
  import FeatureBar from "../lib/components/FeatureBar.svelte";

  const authorize = "/api/auth/login"



</script>



<div class="index-page">
  <div class="index-top">
    <div class="title">Export and back up your Spotify library.</div>
    <SpotifyButton goto="{authorize}">Login With Spotify</SpotifyButton>
  </div>

  <hr class="divider">
  <h2>Features</h2>

  <FeatureBar features={InfoBoxes}/>

</div>

<style lang="scss">

  .index-page {

    padding: 1rem;

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
    }

    .divider {
      border-radius: 5rem;
      border-color: var(--bg-main-50);
      border-width: 0.2rem;
      border-style: solid;
    }

    .index-bottom {
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      gap: 1rem;
      justify-content: space-around;
    }
  }

</style>