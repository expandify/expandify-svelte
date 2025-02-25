<script lang="ts">
	import { createInfiniteQuery } from '@tanstack/svelte-query';
	import { createVirtualizer } from '@tanstack/svelte-virtual';
	import { tracks } from '$lib/stores/library/tracks';
	import { browser } from '$app/environment';
	import { spotifyPersistence } from '$lib/services/spotify/spotify-persistance';

	let virtualListEl: HTMLDivElement;

	const query = createInfiniteQuery({
		queryKey: ['projects'],
		queryFn: ({ pageParam }) => fetchServerPage(10, pageParam),
		initialPageParam: 0,
		getNextPageParam: (_lastGroup, groups) => groups.length
	});

	$: allRows =
		($query.data && $query.data.pages.flatMap((page) => page.rows)) || [];

	$: virtualizer = createVirtualizer<HTMLDivElement, HTMLDivElement>({
		count: 0,
		getScrollElement: () => virtualListEl,
		estimateSize: () => 100,
		overscan: 5
	});

	$: {
		$virtualizer.setOptions({
			count: $query.hasNextPage ? allRows.length + 1 : allRows.length
		});

		const [lastItem] = [...$virtualizer.getVirtualItems()].reverse();

		if (
			lastItem &&
			lastItem.index > allRows.length - 1 &&
			$query.hasNextPage &&
			!$query.isFetchingNextPage
		) {
			$query.fetchNextPage();
		}
	}

	let init = true;
	$: {
		if ($tracks && init) {
			console.log("loading", $tracks.loading);
			if (!$tracks.loading) {
				init = false;
				$query.fetchNextPage();
			}
		}
	}

	async function fetchServerPage(
		limit: number,
		offset: number = 0
	): Promise<{ rows: string[]; nextOffset: number }> {

		await spotifyPersistence.loadSavedTracks()
		const rows = $tracks.tracks.slice(offset * limit, (offset * limit) + limit).map(a => a.name)
		//const rows = new Array(limit).map((_e, i) =>  $tracks.tracks[i + (offset - 1) * limit])
		//const rows = new Array(limit).fill(0).map((_e, i) => `Async loaded row #${i + (offset) * limit}`);
		console.log(rows);

		console.log($tracks);
		console.log($tracks.tracks);
		console.log(offset, limit, browser);
		console.log(offset * limit, limit);
		console.log($tracks.tracks.slice(offset * limit, (offset * limit) + limit));
		return { rows, nextOffset: offset + 1 };
	}
</script>

<main>
	<p>
		This infinite scroll example uses Svelte Query's createInfiniteQuery
		function to fetch infinite data from a posts endpoint and then a
		rowVirtualizer is used along with a loader-row placed at the bottom of the
		list to trigger the next page to load.
	</p>
	<br />
	<br />

	{#if $query.isLoading}
		Loading...
	{:else if $query.isError}
		<span>Error: {$query.error.message}</span>
	{:else if $query.isSuccess}
		<div class="list scroll-container" bind:this={virtualListEl}>
			<div
				style="position: relative; height: {$virtualizer.getTotalSize()}px; width: 100%;"
			>
				{#each $virtualizer.getVirtualItems() as row (row.index)}
					<div
						class:list-item-even={row.index % 2 === 0}
						class:list-item-odd={row.index % 2 === 1}
						style="position: absolute; top: 0; left: 0; width: 100%; height: {row.size}px; transform: translateY({row.start}px);"
					>
						{#if row.index > allRows.length - 1}
							{#if $query.hasNextPage}
								Loading more...
							{:else}
								Nothing more to load
							{/if}
						{:else}
							{allRows[row.index]}
						{/if}
					</div>
				{/each}
			</div>
		</div>
	{/if}
	{#if $query.isFetching && !$query.isFetchingNextPage}
		<p>Background updating...</p>
	{/if}
</main>

<style>
    .scroll-container {
        height: 500px;
        width: 100%;
        overflow: auto;
    }
</style>
