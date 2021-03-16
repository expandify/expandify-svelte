<template>
  <footer class="footer">
    <a href="https://phoenixframework.org/" class="phx-logo">
      <img class="phx-img" src="@/assets/phoenix.png" alt="Phoenix Framework Logo" />
    </a>
    <div>&copy; Copyright Till Wittenburg</div>
    <a href="https://github.com/systeno" class="github">
      <fa-icon class="github" :icon="['fab', 'github']" /> GitHub
    </a>
  </footer>
</template>

<script>
export default {
  name: "ExporitfyFooter"
};
</script>

<style lang="scss">
@use "src/styles/colors";
@use "src/styles/sizes";
@use "src/styles/fonts";

.footer {
  height: sizes.$footer;
  text-align: center;
  align-items: center;
  padding: 8px;
  background-color: colors.$black;
  display: flex;
  justify-content: space-between;
  z-index: 2;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  font-weight: fonts.$normal_weight;
  font-size: fonts.$body_size;
  font-family: fonts.$font-stack;
  border-radius: sizes.$border_radius;

  .phx-logo {
    height: 100%;
    flex-basis: 0;
    margin: 0;
    min-width: unset;
  }

  .phx-img {
    height: 100%;
    width: auto;
    max-width: unset;
  }

  .github {
    color: colors.$light_gray;
  }
}
</style>
