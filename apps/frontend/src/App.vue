<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style lang="scss">
@use "styles/colors";
@use "styles/fonts";
@import "~normalize.scss";

* {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

html,
body {
  background-color: colors.$gray;
  color: colors.$white;
  min-height: 100vh;
  font-family: fonts.$font-stack;
  font-weight: normal;
}
</style>
