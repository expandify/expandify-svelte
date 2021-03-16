<template>
  <button class="nav-button" @click="handleClick"><slot /></button>
</template>

<script>
export default {
  name: "NavButton",
  methods: {
    handleClick(evt) {
      this.$emit("click", evt);
    }
  }

}
</script>

<style scoped lang="scss">
@use "src/styles/colors";
@use "src/styles/fonts";
@use "src/styles/sizes";

.nav-button {
  background: colors.$gray;
  cursor: pointer;
  color: colors.$white;
  transition-duration: .3s;
  box-sizing: border-box;
  font-family: fonts.$font-stack;
  font-size: fonts.$nav_button_size;
  font-weight: fonts.$thick;
  letter-spacing: 2px;
  text-align: center;
  vertical-align: middle;
  border-radius: sizes.$border_radius;
  margin: 0;
  padding: 15px 10px 15px 10px;
  width: sizes.$nav_button_width;
  display: inline-block;
  text-decoration: none;
  border: none;
}

.nav-button:hover {
  background: lighten(colors.$gray, 10%);
  color: colors.$white;
}
</style>