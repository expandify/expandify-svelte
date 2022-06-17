<script lang="ts">
  import { libraryStore } from "../stores/library";

  export let libraryId: string | null | undefined
  
</script>

<div class="comparing-div"
  class:accent-green={libraryId === $libraryStore.activeLibrary?.id}
  class:accent-blue={libraryId === $libraryStore.compareTo?.id}
  class:accent-div={libraryId && $libraryStore.compareTo?.id && $libraryStore.activeLibrary?.id}>

  <slot />
</div>

<style lang="scss">
  .comparing-div {
    width: 100%;
    height: 100%;
  }

  .accent-div {
    color: var(--accent);    
  }
</style>
