<script>
  import {page} from "$app/stores";
  import {config} from "../../../stores/config.js";
  import {RefreshCwIcon} from "svelte-feather-icons"
  import IconLink from "../../../lib/components/elements/IconLink.svelte";
  import {Library} from "../../../shared/classes/Library";
  import {onDestroy} from "svelte";
  import LoadingDots from "../../../lib/components/elements/LoadingDots.svelte";
  import {browser} from "$app/env";

  config.update(value => ({
    ...value,
    currentLibrary: value.currentLibrary || $page.params?.library || Library.Type.current
  }))
  let myInterval = undefined
  let loading
  let proposeRefresh
  $: proposeRefresh = false

  $: loading = false

  async function refreshLibrary() {
    loading = true
    const response = await fetch($page.url.href + "/__data.json", {method: 'POST'})
    if (!response.ok) {
      throw new Error("error updating the current Library")
    }
    myInterval = setInterval(async () => {
      const response = await fetch($page.url.href + "/__data.json")
      if (response.status !== 202) {
        clearInterval(myInterval)
        loading = false
        proposeRefresh = true
      }
    }, 500);
  }

  function refreshPage() {
    if (browser) {
      proposeRefresh = false
      window.location.reload();
    }
  }

  onDestroy(() => clearInterval(myInterval));


</script>

<div class="library-head">
  Library: {$config.currentLibrary}
  {#if proposeRefresh}
    <div on:click={refreshPage}>
      <IconLink icon={RefreshCwIcon}>Refresh the Page to see the updated Library</IconLink>
    </div>
  {:else if loading}
    <LoadingDots/>
  {:else if $config.currentLibrary === Library.Type.current }
    <div on:click={refreshLibrary}>
      <IconLink icon={RefreshCwIcon}>Refresh Library</IconLink>
    </div>
  {/if}
</div>

<slot/>


<style lang="scss">

  .library-head {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin-bottom: 2rem;
  }
</style>
