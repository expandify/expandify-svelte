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
  margin-left: 50px;
  margin-right: 50px;
  width: 300px;
  height: 300px;
  overflow: hidden;

  .title {
    color: colors.$green;
    font-weight: 700;
    font-size: fonts.$h2_size;
    text-shadow: 0 0 1px colors.$black;
    margin-bottom: 0;
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
  }
}
</style>