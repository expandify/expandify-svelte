<script lang="ts">
	import type { Album, Artist, Playlist } from '$lib/types/spotify';
	import { fade } from 'svelte/transition';
	import ImageWithFallback from '../common/ImageWithFallback.svelte';
	import { Card, Popover } from 'flowbite-svelte';

	export let card: Album | Artist | Playlist;

	let title = card.name;
	let subtitle: string;
	let href: string | null;

	switch (card.type) {
		case 'album':
			subtitle = card.artists.map(a => a.name).join(', ');
			href = `/library/album/${card.id}`;
			break;
		case 'artist':
			subtitle = 'Artist';
			href = `/library/artist/${card.id}`;
			break;
		case 'playlist':
			subtitle = `By ${card.owner.display_name}`;
			href = `/library/playlist/${card.id}`;
			break;

	}

</script>

<Card img="{card?.images?.at(0)?.url}"
			href={href}
			reverse={false}
			class="p-4 max-w-48 rounded-2xl"
			imgClass="mb-4 rounded-2xl"
			padding="none">
	<div id="card-title-{card.id}">
		<h5 class="font-bold text-xl text-black dark:text-white overflow-ellipsis overflow-hidden whitespace-nowrap">
			{title}
		</h5>
		<p class="overflow-ellipsis overflow-hidden whitespace-nowrap">{subtitle || ""}</p>
	</div>
	<Popover triggeredBy="#card-title-{card.id}"
					 placement="bottom"
					 arrow="{false}"
					 transition={fade}
					 params={{ duration: 200, delay: 500 }}
					 offset={-50} >
		<h5 class="font-bold text-xl text-black dark:text-white">
			{title}
		</h5>
		<p class="">{subtitle || ""}</p>
	</Popover>
</Card>
