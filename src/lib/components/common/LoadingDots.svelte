<script lang="ts">
  import {onDestroy} from "svelte";

  export let message: string;

  let progressDots = ""

  const interval = setInterval(() => {
    if (progressDots.length > 3)
      progressDots = "";
    else
      progressDots += ".";
  }, 400);

  onDestroy(() => clearInterval(interval));
</script>

<div>{message}{progressDots}</div>


<style lang="scss">

</style>