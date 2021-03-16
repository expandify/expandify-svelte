<template>
  <div class="playlists">
    <TitleBar></TitleBar>
    <NavBar></NavBar>
    <Footer></Footer>
  </div>
</template>

<script>
import TitleBar from "@/components/TitleBar";
import Footer from "@/components/Footer";
import NavBar from "@/components/NavBar";

export default {
  name: "Playlists",
  components: {
    NavBar,
    TitleBar,
    Footer,
  },
}
</script>

<style scoped>

</style>