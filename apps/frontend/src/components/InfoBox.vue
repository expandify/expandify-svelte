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
@use "src/styles/fonts";
@use "src/styles/sizes";

.box {
  margin: 50px;
  width: 350px;
  height: 400px;
  overflow: hidden;
  border-radius: sizes.$border_radius;
  background-color: var(--text-box-bg-color);
  padding-left: 20px;
  padding-right: 20px;

  .title {
    color: var(--text-box-title-color);
    font-weight: 700;
    font-size: fonts.$h2_size;
    margin-bottom: 0;
    text-align: center;
  }
  .empty {
    margin-bottom: 20px;
  }

  .warning {
    color: var(--text-box-warning-color);
    font-weight: 600;
    font-size: fonts.$h3_size;
    margin-bottom: 0;
    margin-top: 0;  
    min-height: 20px;
  }

  .text {
    color: var(--text-box-text-color);
    font-size: fonts.$body_size;
    text-align: justify;
  }
}
</style>