<script lang="ts">
    import type {PlaybackState} from "$lib/types/spotify";
    import {msToTime} from "$lib/utils/converter/date-time";
    import {onDestroy} from "svelte";
    import ImageWithFallback from "../common/ImageWithFallback.svelte";
    import ProgressBar from "../common/ProgressBar.svelte";
    import {toPlaybackState} from "$lib/utils/converter/spotify";
    import {spotifyApi} from "$lib/services/spotify/spotify-api";
    import {
        BackwardStepSolid,
        ForwardStepSolid,
        PauseSolid,
        PlaySolid
    } from 'flowbite-svelte-icons';
    import { Button, Card } from 'flowbite-svelte';


    let playbackState = $state<PlaybackState | null>();

    let timeMs = $derived(playbackState?.progress_ms || 0);
    let lengthMs = $derived(playbackState?.item?.duration_ms || 1);
    let remainingMs = $derived(lengthMs - timeMs);
    let percent = $derived(((timeMs / lengthMs) * 100).toFixed(1));

    async function getPlayback() {
        const playbackState = await spotifyApi.getPlaybackState();
        if (!playbackState) {
            return null;
        }
        return toPlaybackState(playbackState);
    }

    (async () => {
        playbackState = await getPlayback();
    })();

    const playbackInterval = setInterval(async () => {
        playbackState = await getPlayback();
    }, 1000);

    onDestroy(() => {
        clearInterval(playbackInterval);
    })
</script>

{#key playbackState}
    {#if playbackState}
        <Card class="aspect-video flex flex-col justify-between gap-2">
            <div class="header flex flex-row h-[50%] gap-4">
                <ImageWithFallback type={playbackState.item?.album} fallbackSvg="album"/>
                <div class="infos flex flex-col">
                    <h2 class="title">{playbackState.item?.name}</h2>
                    <h5 class="artists">{playbackState.item?.artists.map((a) => a.name).join(', ')}</h5>
                    <small class="device">{playbackState.device.name}</small>
                </div>
            </div>

            <div class="progress flex flex-row items-center justify-between gap-4 group">

                <small class="time w-[10%] group-hover:hidden">{msToTime(timeMs)}</small>
                <small class="remaining-time w-[10%] hidden group-hover:block">{msToTime(remainingMs)}</small>
                <ProgressBar max={lengthMs} value={timeMs}></ProgressBar>
                <small class="length w-[10%] group-hover:hidden">{msToTime(lengthMs)}</small>
                <small class="percent w-[10%] hidden group-hover:block">{percent}%</small>
            </div>


            <div class="flex flex-row justify-between">
                <button onclick={() => spotifyApi.previousPlayback()}>
                    <BackwardStepSolid class="h-14 w-14" />
                </button>
                {#if playbackState.is_playing}
                    <button class="media-control-button" onclick={() => spotifyApi.pausePlayback()}>
                        <PauseSolid class="h-14 w-14" />
                    </button>
                {:else}
                    <button class="media-control-button" onclick={() => spotifyApi.startPlayback()}>
                        <PlaySolid class="h-14 w-14" />
                    </button>
                {/if}
                <button class="media-control-button" onclick={() => spotifyApi.nextPlayback()}>
                    <ForwardStepSolid class="h-14 w-14" />
                </button>
            </div>

        </Card>
    {/if}
{/key}

<Button></Button>