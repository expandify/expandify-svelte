<script lang="ts">
  import {page, session} from "$app/stores";
  import {LibrarySimplified, LibraryType} from "$lib/types/Library";
  import {libraryStore} from "$lib/client/stores/library.js";
  import {onMount} from "svelte";

  onMount(async () => {

    const currentId = $page.params?.library || LibraryType.current
    const compareId = $page.url.searchParams.get("compare-to") || null

    const response = await fetch(`${$session.BASE_URL}/libraries/${currentId}`)
    const json: {library: LibrarySimplified } = await response.json()
    const activeLibrary = json.library

    let compareLibrary: LibrarySimplified | null = null
    if (compareId !== null) {
      const response = await fetch(`${$session.BASE_URL}/libraries/${compareId}`)
      const json: {library: LibrarySimplified } = await response.json()
      compareLibrary = json.library
    }

    libraryStore.set({
      activeLibrary: activeLibrary,
      compareTo: compareLibrary
    })

  })


</script>

<slot/>
