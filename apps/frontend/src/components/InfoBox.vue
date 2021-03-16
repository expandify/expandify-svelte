<template>
  <div class="box">
    <h2 :class="{ title: true, empty: !warning }">{{ title }}</h2>
    <div v-if="warning" class="warning">{{ warning }}</div>
    <div class="text"><slot></slot></div>
  </div>
</template>

<script>
export default {
  name: "InfoBox",
  props: {
    title: {
      type: String,
      required: true
    },
    warning: String
  }
};
</script>

<style scoped lang="scss">
@use "src/styles/colors";
@use "src/styles/fonts";

.box {
  margin: 50px;
  width: 350px;
  height: 400px;
  overflow: hidden;
  border-radius: 20px;
  background-color: colors.$black;
  padding-left: 20px;
  padding-right: 20px;

  .title {
    color: colors.$green;
    font-weight: 700;
    font-size: fonts.$h2_size;
    text-shadow: 0 0 1px colors.$black;
    margin-bottom: 0;
    text-align: center;
  }
  .empty {
    margin-bottom: 20px;
  }

  .warning {
    color: colors.$warning;
    font-weight: 600;
    font-size: fonts.$h3_size;
    text-shadow: 0 0 1px colors.$black;
    margin-bottom: 0;
    margin-top: 0;  
    min-height: 20px;
  }

  .text {
    font-size: fonts.$body_size;
    text-align: justify;
  }
}
</style>