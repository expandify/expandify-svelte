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
@use "src/styles/colors";
@use "src/styles/fonts";

.spotify-button {
  background-color: colors.$green;
  cursor: pointer;
  color: colors.$white;
  transition-duration: .3s;
  box-sizing: border-box;
  font-size: fonts.$button_size;
  font-weight: 700;
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
  background-color: colors.$green_hover;
  color: #e1e1e1;
}
</style>
