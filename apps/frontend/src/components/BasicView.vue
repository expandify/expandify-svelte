<template>
  <div class="view">
    <BackgroundImage>
      <TitleBar></TitleBar>
      <NavBar></NavBar>
      <Footer></Footer>
      <div class="content">
        <slot>

        </slot>
      </div>
    </BackgroundImage>
  </div>

</template>

<script>
import TitleBar from "@/components/TitleBar";
import Footer from "@/components/Footer";
import NavBar from "@/components/NavBar";
import BackgroundImage from "@/components/BackgroundImage";

export default {
  name: "BasicView",
  components: {
    BackgroundImage,
    NavBar,
    TitleBar,
    Footer
  }
};
</script>

<style scoped lang="scss">
@use "src/styles/fonts";
@use "src/styles/colors";
@use "src/styles/sizes";


.content {
  min-height: 100vh;
  padding-top: sizes.$title_bar;
  padding-bottom: sizes.$footer;
  padding-left: sizes.$side_bar;
}
</style>