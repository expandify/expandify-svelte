<script>
  import {onDestroy} from "svelte";
  import {STORE_STATUS} from "../../library/store.js";

  export let max = null;
  export let current = null;
  export let name = "Something"
  export let status = STORE_STATUS.INIT

  let progressDots = ""
  $: progress = (current / max) * 100


  const interval = setInterval(() => {
    if (progressDots.length > 3)
      progressDots = "";
    else
      progressDots += ".";
  }, 400);

  onDestroy(() => clearInterval(interval));
</script>

<div class="wrapper">
  {#if status === STORE_STATUS.FETCHING}
    <h1 class="title">Loading {name}: {current} of {max}</h1>
    <div class="progress">
      <div class="pill" style:width="{progress}%"></div>
    </div>
  {:else}
    <h1 class="title">Loading {name} {progressDots}</h1>
  {/if}
</div>

<style lang="scss">

  .wrapper {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;

    .progress {
      background-color: var(--bg-main-100);
      border-radius: 2rem;
      padding: 0.3rem;

      .pill {
        background-color: var(--accent);
        height: 1rem;
        border-radius: 1rem;
      }
    }
  }



</style>