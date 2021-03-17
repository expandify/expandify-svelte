<template>
  <div class="background">
    <slot> </slot>
  </div>
</template>

<script>
export default {
  name: "BackgroundImage"
};
</script>

<style scoped lang="scss">
@use "src/styles/colors";

@keyframes gradient {
  0% {
    background-position: 0% 0%;
  }
  50% {
    background-position: 100% 100%;
  }
  100% {
    background-position: 0% 0%;
  }
}
.background {
  background-image: colors.$background_gradiant;
  background-size: 400% 400%;
  animation: gradient 120s linear infinite;
}

</style>