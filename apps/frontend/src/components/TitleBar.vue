<template>
  <div class="title-bar">
    <h1 class="title" @click="goHome">Exportify</h1>
  </div>
</template>

<script>
export default {
  name: "TitleBar",
  methods: {
    goHome() {
      this.$router.push({ name: "Home" });
    }
  }
}
</script>

<style scoped lang="scss">
@use "src/styles/fonts";
@use "src/styles/sizes";

.title-bar {
  position: fixed;
  height: sizes.$title_bar;
  left: 0;
  right: 0;
  top: 0;
  text-align: center;
  background-color: var(--title-bg-color);
  z-index: 2;

  .title {
    margin-top: 0;
    font-size: fonts.$title_size;
    font-family: fonts.$title_font_stack;
    color: var(--title-font-color);
  }
  .title:hover {
    color: var(--title-hover-font-color);
    cursor: pointer;
  }
}
</style>