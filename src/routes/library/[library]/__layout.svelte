<script lang="ts">
  import {page} from "$app/stores";
  import {LibraryType} from "$lib/shared/types/Library";
  import {libraryStore} from "$lib/client/stores/library.js";

  libraryStore.update(value => ({
    ...value,
    currentLibrary: $page.params?.library || LibraryType.current
  }))


</script>

<slot/>


<style lang="scss">

  .library-head {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin-bottom: 2rem;
  }
</style>
