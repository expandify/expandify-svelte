<script context="module" lang="ts">
  import type { Load } from '@sveltejs/kit';

  export const load: Load = async function({session}) {
    if (!session.loggedIn) {
      return { redirect: "/", status: 302}
    }

    return { props: {} }
  }
</script>


<slot/>
