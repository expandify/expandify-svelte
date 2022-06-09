<script>

  export let goto = "/"

</script>

<a class="spotify-button" href={goto}>
  <div class="content">
    <slot/>
  </div>
</a>

<style lang="scss">
  .spotify-button {
    background-color: #1DB954;
    cursor: pointer;
    color: #ffffff;
    letter-spacing: 0.2rem;
    display: flex;
    justify-content: center;
    border-radius: 500px;
    width: 20rem;
    height: 3rem;

    .content {
      margin-top: auto;
      margin-bottom: auto;
    }
  }
  .spotify-button:hover {
    background-color: #1ed760;
  }
</style>
