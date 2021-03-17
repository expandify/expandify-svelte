<template>
  <div class="home">
    <BackgroundImage>
      <div class="login-top">

      </div>
    </BackgroundImage>
  </div>
</template>

<script>
// @ is an alias to /src
import BackgroundImage from "@/components/BackgroundImage";

export default {
  name: "Albums",
  components: {
    BackgroundImage,
  },
};
</script>


<style scoped lang="scss">
@use "src/styles/fonts";
@use "src/styles/colors";

$top-size: 700px;

.login-top {
  height: $top-size;
}
</style>
