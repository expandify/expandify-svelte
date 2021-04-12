<template>
  <BasicView>
    <h1>{{ todos }}</h1>
  </BasicView>
</template>

<script>
import BasicView from "@/components/BasicView";
import axios from "axios";

export default {
  name: "Playlists",
  components: {
    BasicView
  },
  data() {
    return {
      todos: null
    };
  },
  methods: {
    async login() {
      window.location.href = "localhost:4001/";
    },
    getPlaylists: function() {
      axios
        .get("https://jsonplaceholder.typicode.com/todos")
        .then(response => (this.todos = response));
    }
  },
  beforeMount() {
    this.getPlaylists();
  }
};
</script>

<style scoped lang="scss">
@use "src/styles/fonts";
</style>
