<script lang="ts">
	import type { PlaybackState } from '$lib/types/spotify';
	import { msToTime } from '$lib/utils/converter/date-time';
	import { onDestroy, onMount } from 'svelte';
	import { toPlaybackState } from '$lib/utils/converter/spotify';
	import { spotifyApi } from '$lib/services/spotify/spotify-api';
	import { Disc3, Pause, Play, SkipBack, SkipForward } from 'lucide-svelte';
	import { Card } from '$lib/components/ui/card';
	import { CardContent } from '$lib/components/ui/card/index.js';
	import { Progress } from '$lib/components/ui/progress';
	import { Button } from '$lib/components/ui/button';


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
	});


	let autoScrollElement = $state<Element>();
	onMount(() => {
		let reverse = false;
		let scroll = 0;
		window.setInterval(function() {
			if (scroll <= 0) {
				reverse = false;
			}
			if (autoScrollElement && ((scroll + autoScrollElement.clientWidth) >= autoScrollElement.scrollWidth)) {
				reverse = true;
			}
			scroll = reverse ? scroll - 1 : scroll + 1;
			autoScrollElement?.scrollTo({left: scroll, behavior: 'smooth'});
		}, 60);
	});

</script>


<!--{#key playbackState}-->
	{#if playbackState}
		<Card class="h-full">
			<CardContent class="h-full flex flex-col gap-4">
				<div class="flex flex-col h-full gap-4">
					{#if playbackState.item}
						<img src={playbackState.item?.album.images?.at(0)?.url}
								 class="rounded-2xl"
								 alt={playbackState.item?.album.name}
								 loading="lazy" />
					{:else}
						<Disc3 size="80" />
					{/if}

					<div class="flex flex-col whitespace-nowrap">
						<div bind:this={autoScrollElement} class="whitespace-nowrap overflow-hidden">
							<b>{playbackState.item?.artists.map((a) => a.name).join(', ')}</b>
							&bull;
							{playbackState.item?.name}
						</div>
						<small>{playbackState.device.name}</small>
					</div>
				</div>


				<div class="progress flex flex-row items-center justify-between gap-4 group">

					<small class="time w-[10%] group-hover:hidden">{msToTime(timeMs)}</small>
					<small class="remaining-time w-[10%] hidden group-hover:block">{msToTime(remainingMs)}</small>
					<Progress max={lengthMs} value={timeMs} class="h-2" />
					<small class="length w-[10%] group-hover:hidden">{msToTime(lengthMs)}</small>
					<small class="percent w-[10%] hidden group-hover:block">{percent}%</small>
				</div>


				<div class="flex flex-row justify-between">
					<Button variant="ghost"
									onclick={() => spotifyApi.previousPlayback()}>
						<SkipBack />
					</Button>

					{#if playbackState.is_playing}
						<Button variant="ghost" onclick={() => spotifyApi.pausePlayback()}>
							<Pause />
						</Button>
					{:else}
						<Button variant="ghost" onclick={() => spotifyApi.startPlayback()}>
							<Play />
						</Button>
					{/if}
					<Button variant="ghost" onclick={() => spotifyApi.nextPlayback()}>
						<SkipForward />
					</Button>
				</div>
			</CardContent>
		</Card>
	{/if}
<!--{/key}-->
