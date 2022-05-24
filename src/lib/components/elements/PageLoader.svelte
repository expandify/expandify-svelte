
<script>
  import { onDestroy, onMount } from 'svelte';
  import { tweened } from 'svelte/motion';
  import { cubicOut } from 'svelte/easing';
  import {navigating} from "$app/stores";
  import {fade} from "svelte/transition";
  const progress = tweened(0, {
    duration: 5000,
    easing: cubicOut
  });

  const unsubscribe = navigating.subscribe((state) => {
    if (state ===  null) {
      progress.set(1, { duration: 1000 });
    }
  });

  onMount(() => {
    progress.set(0.7);
  });
  onDestroy(() => {
    unsubscribe();
  });
</script>

<div class="progress-bar" out:fade={{ delay: 500 }} >
  <div class="progress-sliver" style:width="{$progress * 100}%" ></div>
</div>

<style lang="scss">
  .progress-bar {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 0.3rem;

    .progress-sliver {
      border-radius: 5rem;
      background-color: var(--accent);
      height: 100%;
    }
  }

</style>