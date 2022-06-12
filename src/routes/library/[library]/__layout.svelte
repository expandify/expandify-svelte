<script lang="ts">
  import {page} from "$app/stores";
  import {LibraryType} from "$lib/types/Library";
  import {libraryStore} from "$lib/client/stores/library.js";

  libraryStore.update(value => ({
    ...value,
    currentLibrary: $page.params?.library || LibraryType.current
  }))


</script>

<slot/>
