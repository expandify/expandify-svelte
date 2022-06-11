<script lang="ts">
  import {onDestroy} from "svelte";

  let progressDots = ""

  const interval = setInterval(() => {
    if (progressDots.length > 3)
      progressDots = "";
    else
      progressDots += ".";
  }, 400);

  onDestroy(() => clearInterval(interval));
</script>

<div class="title">Loading <slot/> {progressDots}</div>


<style lang="scss">

</style>