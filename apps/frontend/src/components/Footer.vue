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
@use "src/styles/sizes";
@use "src/styles/fonts";

.footer {
  height: sizes.$footer;
  text-align: center;
  align-items: center;
  padding: 8px;
  background-color: var(--footer-bg-color);
  display: flex;
  justify-content: space-between;
  z-index: 2;
  bottom: 0;
  font-weight: fonts.$normal_weight;
  font-size: fonts.$body_size;
  font-family: fonts.$font-stack;
  color: var(--footer-text-color);

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
    color: var(--footer-text-color);
  }
}
</style>
