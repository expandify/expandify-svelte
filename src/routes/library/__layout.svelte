<script context="module">
  export async function load({session}) {
    if (!session.loggedIn) {
      return { redirect: "/", status: 302}
    }
    return { props: {} }
  }
</script>

<script>
</script>


<slot/>

<style lang="scss">
</style>
