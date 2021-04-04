<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style lang="scss">
@use "styles/fonts";
@use "styles/colors";
@import "~normalize.scss";

* {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

html,
body {
  background-color: var(--main-bg-color);
  color: var(--main-text-color);
  min-height: 100vh;
  font-family: fonts.$font-stack;
  font-weight: normal;
}
</style>
