<script lang="ts">
    import {onDestroy} from "svelte";
    import {createEventDispatcher} from "svelte";
    import Button from "./Button.svelte";

    const dispatch = createEventDispatcher();

    export let title: string;
    export let total: number | null = null;
    export let current: number | null = null;
    export let loading: boolean = true;
    export let error: boolean = false;


    let progressDots = ""
    const interval = setInterval(() => {
        if (progressDots.length > 3)
            progressDots = "";
        else
            progressDots += ".";
    }, 400);
    onDestroy(() => clearInterval(interval));

    $: stepSuffix = current && total ? `: ${current} of ${total}` : "";


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
            <Button on:click={() => dispatch('retry')} text="Retry"/>
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
