<template>
  <button class="spotify-button" @click="handleClick">Login With Spotify</button>
</template>

<script>
export default {
  name: "SpotifyButton",
  methods: {
    handleClick(evt) {
      this.$emit("click", evt);
    }
  }

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
@use "src/styles/fonts";
@use "src/styles/sizes";

.spotify-button {
  background-color: var(--button-bg-color);
  cursor: pointer;
  color: var(--button-text-color);
  transition-duration: .3s;
  box-sizing: border-box;
  font-family: fonts.$font-stack;
  font-size: fonts.$button_size;
  font-weight: fonts.$thick;
  letter-spacing: 2px;
  text-align: center;
  vertical-align: middle;
  text-transform: uppercase;
  border-radius: 500px;
  margin: 30px 0;
  padding: 15px 10px 15px 10px;
  min-width: 300px;
  display: inline-block;
  text-decoration: none;
  border: none;
}

.spotify-button:hover {
  background-color:   var(--button-hover-bg-color);
  color: var(--button-text-color);
}
</style>
