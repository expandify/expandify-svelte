<template>
  <div class="home">
    <div class="login-top">
      <TitleBar/>
      <div class="greeting"> Export and back up your Spotify library.</div>
      <SpotifyButton @click="login">Login With Spotify</SpotifyButton>
    </div>

    <div class="login-bottom">
      <InfoBox title="Login with Spotify">
        Login with your Spotify account, to see your library.
        Exportify will show you your: Playlists, Albums, Artists and Songs.
        Exportify cannot delete or create anything. It will only read your existing data.
      </InfoBox>
      <InfoBox title="Download your library">
        Exportify gives you the ability to download your current library state.
        Since this app is still in early development, the format of the downloaded data may change over time.
      </InfoBox>
      <InfoBox title="Backup your library" warning="Warning: experimental feature">
        <p class="mt0">
          Exportify also gives you the ability to safe your library online.
          It is connected to your Spotify ID, so you dont need an additional account.
        </p>
        <p>
          <b>Do not</b> rely on this <b>yet</b>. This is feature is not ready for serious use at this time.
        </p>
      </InfoBox>
      <InfoBox title="Lorem Ipsum">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras iaculis blandit facilisis. Vestibulum a feugiat
        diam, eget egestas enim. Proin varius neque quis orci gravida efficitur nec at libero. Ut ut odio eu est
        vestibulum ullamcorper sed eu mauris. Sed ac dapibus diam. Quisque congue pellentesque pellentesque. Cras
        blandit, est id aliquet vulputate, erat est euismod massa, nec hendrerit neque neque in magna. Nam non felis
        dui. Phasellus molestie turpis pharetra venenatis imperdiet. Suspendisse potenti. In dignissim odio suscipit,
        euismod purus vel, laoreet dui.

        Vivamus id nisl mi. Sed ac consequat lacus, nec euismod sem. Vivamus scelerisque felis sed efficitur ornare.
        Aliquam auctor aliquam ipsum quis lobortis. Fusce massa nibh, sodales sollicitudin enim ac, tincidunt
        sollicitudin est. Ut diam eros, imperdiet sollicitudin vestibulum quis, ornare eget tortor. Pellentesque
        facilisis nisi vitae purus convallis, in volutpat elit cursus. Morbi egestas tempor quam, ut dignissim mauris
        posuere nec. Vivamus ultrices mi ut consequat porta. Duis turpis leo, ultricies at pretium nec, efficitur ut
        est.
      </InfoBox>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
// @ is an alias to /src
import SpotifyButton from "@/components/SpotifyButton.vue";
import Footer from "@/components/Footer";
import InfoBox from "@/components/InfoBox";
import TitleBar from "@/components/TitleBar";

export default {
  name: "Home",
  components: {
    TitleBar,
    InfoBox,
    Footer,
    SpotifyButton
  },
  methods: {
    async login() {
      window.location.href = "localhost:4001/";
    }
  }
};
</script>


<style scoped lang="scss">
@use "src/styles/fonts";
@use "src/styles/colors";

$top-size: 700px;

@keyframes gradient {
  0% {
    background-position: 0% 0%;
  }
  50% {
    background-position: 100% 100%;
  }
  100% {
    background-position: 0% 0%;
  }
}

.home {
  display: flex;
  flex-direction: column;
}

.login-top {
  background-size: 400% 400%;
  animation: gradient 120s linear infinite;
  height: $top-size;
  background-image: colors.$background_gradiant;
  align-items: center;
  display: flex;
  flex-direction: column;
  justify-content: center;

  .greeting {
    align-items: center;
    font-size: fonts.$h1_size;
    font-family: fonts.$title_font_stack;
    color: colors.$white;
    text-shadow: 0 0 5px black;
  }

}

.login-bottom {
  display: flex;
  flex: 1;
  justify-content: space-around;
  flex-wrap: wrap;
  flex-direction: row;
  padding: 50px 300px;
  z-index: 1;
  box-shadow: 0 0 10px black;
  min-height: calc(100vh - #{$top-size});
  .mt0 {
    margin-top: 0;
  }

}
</style>
