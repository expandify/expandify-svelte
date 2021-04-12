<template>
  <div class="view">
    <TitleBar></TitleBar>
    <NavBar></NavBar>
    <div class="content">
      <div>
        <slot></slot>
      </div>
      <Footer></Footer>
    </div>
  </div>
</template>

<script>
import TitleBar from "@/components/TitleBar";
import Footer from "@/components/Footer";
import NavBar from "@/components/NavBar";

export default {
  name: "BasicView",
  components: {
    NavBar,
    TitleBar,
    Footer
  }
};
</script>

<style scoped lang="scss">
@use "src/styles/fonts";
@use "src/styles/sizes";

.content {
  min-height: 100vh;
  display: flex;
  padding-top: sizes.$title_bar;
  padding-left: sizes.$side_bar;
  flex-direction: column;
  justify-content: space-between;
}
</style>
