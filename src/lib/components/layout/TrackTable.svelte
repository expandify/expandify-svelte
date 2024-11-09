<script lang="ts">
	import type { Track } from '$lib/types/spotify';
	import { formateDate, msToTime } from '$lib/utils/converter/date-time';
	import { TableCell, TableHeader, TableBody, TableRow, Table, TableHead } from '$lib/components/ui/table/index.js';
	import { Tooltip, TooltipProvider, TooltipTrigger, TooltipContent } from '$lib/components/ui/tooltip/index.js';
	import { AspectRatio } from '$lib/components/ui/aspect-ratio';
	import { CardDescription, CardTitle } from '$lib/components/ui/card';

	let { tracks, showImage = true, showAddedAt = false } = $props<{
		tracks: (Track & { added_at?: string })[];
		showImage?: boolean;
		showAddedAt?: boolean;
	}>();

</script>

<Table class="max-w-full">
	<TableHeader>
		<TableRow>
			<TableHead>#</TableHead>
			{#if showImage}
				<TableHead class="w-20"></TableHead>
			{/if}
			<TableHead>TITLE</TableHead>
			<TableHead>ALBUM</TableHead>
			{#if showAddedAt}
				<TableHead>ADDED AT</TableHead>
			{/if}
			<TableHead>TIME</TableHead>
		</TableRow>
	</TableHeader>
	<TableBody>
		{#each tracks as track, i}
			<TableRow onclick={() => console.log(`clicked row ${i + 1}`)}>
				<TableCell>{i + 1}</TableCell>
				{#if showImage}
					<TableCell>
						<AspectRatio ratio={1} class="w-full min-h-full">
							<img src={track.album?.images?.at(0)?.url}
									 class="object-cover "
									 width="100%"
									 alt={track.album?.name}
									 loading="lazy"
							/>
						</AspectRatio>
					</TableCell>
				{/if}
				<TableCell class="text-left">
					<TooltipProvider>
						<Tooltip>
							<TooltipTrigger>
								<div class="w-[40rem] flex flex-col items-start">
									<span
										class="w-full text-left font-bold overflow-hidden whitespace-nowrap overflow-ellipsis">{track.name}</span>
									<span
										class="w-full text-left overflow-hidden whitespace-nowrap overflow-ellipsis">{track.artists.map(a => a.name).join(", ")}</span>
								</div>
							</TooltipTrigger>
							<TooltipContent class="w-56">
								<CardTitle>
									{track.name}
								</CardTitle>
								<CardDescription>
									{track.artists.map(a => a.name).join(", ")}
								</CardDescription>
							</TooltipContent>
						</Tooltip>
					</TooltipProvider>

				</TableCell>
				<TableCell>{track.album?.name}</TableCell>
				<TableCell>{formateDate(track.added_at)}</TableCell>
				<TableCell>{msToTime(track.duration_ms)}</TableCell>
			</TableRow>
		{/each}
	</TableBody>
</Table>