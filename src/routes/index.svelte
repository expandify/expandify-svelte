<script>
  import MenuBar from "$lib/components/MenuBar.svelte"
  import InfoBoxes from "$lib/../assets/info-boxes.json"
  import InfoBox from "$lib/components/InfoBox.svelte";
  import SpotifyButton from "../lib/components/SpotifyButton.svelte";

  const authorize = "/api/auth/login"

</script>


<MenuBar/>

<div class="my-3">
  <div class="title mx-auto mb-6">Export and back up your Spotify library.</div>
  <SpotifyButton goto="{authorize}">Login With Spotify</SpotifyButton>
</div>

<div class="flex flex-row gap-4 flex-wrap justify-around">
  {#each InfoBoxes as infoBox}
    <InfoBox title={infoBox.title} warning={infoBox.warning}>{infoBox.text}</InfoBox>
  {/each}
</div>
