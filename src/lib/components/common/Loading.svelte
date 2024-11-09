<script lang="ts">
    import {onDestroy} from "svelte";
    import { Button } from '$lib/components/ui/button';
    import { Progress } from "$lib/components/ui/progress/index.js";
    import { Check } from 'lucide-svelte';
    import { Separator } from '$lib/components/ui/separator';
    import X from 'lucide-svelte/icons/x';

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

<Separator class="my-4" />
<div class="flex flex-row text-base text-secondary-foreground whitespace-nowrap items-center gap-8 ">

    {#if error}
        <h1>Error loading {title}</h1>
        <X size="28" class="text-red-500" strokeWidth="4" />
        <Button onclick={() => retry()} variant="outline">Retry</Button>
    {:else if loading}
        <h1>{`Loading ${title}${stepSuffix || progressDots}`}</h1>
        <Progress max={total} value={current} class="h-2" />
    {:else}
        <h1>Loaded {title}{stepSuffix}</h1>
        <Check size="28" class="text-green-500" strokeWidth="4" />
    {/if}
</div>
