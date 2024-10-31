<script lang="ts">
    import {onDestroy} from "svelte";
    import Button from "./Button.svelte";

    let {
        title,
        total = null,
        current = null,
        loading = true,
        error = false,
        retry = () => {}
    } = $props<{
        title: string;
        total?: number | null;
        current?: number | null;
        loading?: boolean;
        error?: boolean;
        retry?: () => void;
    }>();


    let progressDots = $state("")
    const interval = setInterval(() => {
        if (progressDots.length > 3)
            progressDots = "";
        else
            progressDots += ".";
    }, 400);
    onDestroy(() => clearInterval(interval));

    let stepSuffix = $derived(current && total ? `: ${current} of ${total}` : "");


</script>

<div class="loading-text-container">
    {#if error}
        <h1 class="text-red-600">Error loading {title}</h1>
    {:else if loading}
        <h1>{`Loading ${title}${stepSuffix || progressDots}`}</h1>
    {:else}
        <h1 class="text-green-500">Loaded {title}{stepSuffix}</h1>
    {/if}

    {#if error}
        <div>
            <Button click={() => retry()} text="Retry"/>
        </div>
    {/if}

</div>
<style lang="scss">
  .loading-text-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 2rem;
  }

</style>
