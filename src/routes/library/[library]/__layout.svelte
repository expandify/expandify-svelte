<script>
  import {page} from "$app/stores";
  import {config} from "../../../stores/config.js";
  import {RefreshCwIcon} from "svelte-feather-icons"
  import IconLink from "../../../lib/components/elements/IconLink.svelte";
  import {Library} from "../../../shared/classes/Library";
  import {onDestroy} from "svelte";
  import LoadingDots from "../../../lib/components/elements/LoadingDots.svelte";

  config.update(value => ({...value, currentLibrary: value.currentLibrary || $page.params?.library || Library.Type.current}))
  let myInterval = undefined
  let loading
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
      }
    }, 500);
  }

  onDestroy(() => clearInterval(myInterval));


</script>

<div class="library-head">
  {#if $config.currentLibrary === Library.Type.current}
    You are browsing your current spotify library.
    <div on:click={refreshLibrary}>
      <IconLink icon={RefreshCwIcon}>Reload Current Library</IconLink>
    </div>
  {:else }
    You are browsing your spotify library from: {$config.currentLibrary}
  {/if}
  {#if loading}
    <LoadingDots/>
  {/if}
</div>

<slot />


<style lang="scss">

  .library-head {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
</style>
