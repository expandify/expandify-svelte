<template>
  <footer class="footer">
    <a href="https://phoenixframework.org/" class="phx-logo">
      <img class="phx-img" src="@/assets/phoenix.png" alt="Phoenix Framework Logo" />
    </a>
    <div>&copy; Copyright Till Wittenburg</div>
    <a href="https://github.com/systeno" class="github">
      <i class="fab fa-github"></i> GitHub
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

.footer {
  height: 40px;
  text-align: center;
  align-items: center;
  padding: 8px;
  background-color: colors.$black;
  display: flex;
  justify-content: space-between;
  box-shadow: 0 0 10px black;
  z-index: 2;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  font-weight: 400;

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
