<script lang="ts">
	import { createVirtualizer } from '@tanstack/svelte-virtual'
	import { tracks } from '$lib/stores/library/tracks';
	import { formateDate, msToTime } from '$lib/utils/converter/date-time';
	import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '$lib/components/ui/table';
	import { AspectRatio } from '$lib/components/ui/aspect-ratio';
	import { CardDescription, CardTitle } from '$lib/components/ui/card';
	import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '$lib/components/ui/tooltip';
	import { createInfiniteQuery } from '@tanstack/svelte-query';

	let virtualListEl: HTMLDivElement

	const virtualizer = $state(createVirtualizer<HTMLDivElement, HTMLDivElement>({
		count: 1,
		getScrollElement: () => virtualListEl,
		estimateSize: () => 50,
		overscan: 5,
	}));


	const query = createInfiniteQuery({
		queryKey: ['projects'],
		queryFn: ({ pageParam }) => {
			return $tracks.tracks.slice(pageParam * 10, (pageParam * 10) + 10);
		},
		initialPageParam: 1,
		getNextPageParam: (_lastGroup, allPages) => allPages.length + 1,
	})

	let allRows = $derived(($query.data && $query.data.pages.flatMap((page) => page)) || []);

	$effect(() => {
		console.log("hello")
		$virtualizer.setOptions({
			count:allRows.length,
		})

		const [lastItem] = [...$virtualizer.getVirtualItems()].reverse()

		if (
			lastItem &&
			lastItem.index > allRows.length - 1 &&
			$query.hasNextPage &&
			!$query.isFetchingNextPage
		) {
			console.log("fetch new")
			$query.fetchNextPage()
		}
	})

</script>

<div class="" bind:this={virtualListEl}>
	<div style="position: relative; height: {$virtualizer.getTotalSize()}px; width: 100%;">



		<Table class="max-w-full">
			<TableHeader>
				<TableRow>
					<TableHead>#</TableHead>
					{#if true}
						<TableHead class="w-20"></TableHead>
					{/if}
					<TableHead>TITLE</TableHead>
					<TableHead>ALBUM</TableHead>
					{#if true}
						<TableHead>ADDED AT</TableHead>
					{/if}
					<TableHead>TIME</TableHead>
				</TableRow>
			</TableHeader>
			<TableBody>
				{#each $virtualizer.getVirtualItems() as row (row.index)}
					{@const track = allRows[row.index]}
					<TableRow onclick={() => console.log(`clicked row ${row.index + 1}`)}>
						<TableCell>{row.index + 1}</TableCell>
						{#if true}
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



	</div>
</div>

