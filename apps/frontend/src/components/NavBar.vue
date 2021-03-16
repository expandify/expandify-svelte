<template>
  <div class="nav-bar">
    <nav class="navigators">
      <NavButton class="nav-item" @click="goto('Playlists')">Playlists</NavButton>
      <NavButton class="nav-item" @click="goto('Albums')">Albums</NavButton>
      <NavButton class="nav-item" @click="goto('Artists')">Artists</NavButton>
      <NavButton class="nav-item" @click="goto('Songs')">Songs</NavButton>
      <NavButton class="nav-item" @click="goto('Libraries')">Libraries</NavButton>
      <NavButton class="nav-item" @click="goto('User')">User</NavButton>
    </nav>

    <nav class="actions">
      <NavButton class="nav-item">Download Library</NavButton>
      <NavButton class="nav-item">Save To Database</NavButton>
    </nav>
  </div>
</template>

<script>
import NavButton from "@/components/NavButton";

export default {
  name: "NavBar",
  components: { NavButton },
  methods: {
    goto(location) {
      this.$router.push({ name: location });
    }
  }
};
</script>

<style scoped lang="scss">
@use "src/styles/colors";
@use "src/styles/sizes";

.nav-bar {
  background: colors.$black;
  border-radius: sizes.$border_radius;
  width: sizes.$side_bar;
  position: fixed;
  left: 0;
  top: sizes.$title_bar;
  bottom: sizes.$footer;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  .navigators {
    top: 0;
    display: flex;
    flex-direction: column;
    justify-content: end;
    .nav-item {
      margin-bottom: 20px;
    }
  }


  .actions {
    bottom: 0;
    .nav-item {
      margin-top: 20px;
    }
  }
}
</style>