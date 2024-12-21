<script lang="ts">
	import type { Album, Artist, Playlist } from '$lib/types/spotify';

	let { card } = $props<{
		card: Album | Artist | Playlist;
	}>();

	let title = card.name;
	let subtitle = $state<string>();
	let href = $state<string | null>();

	switch (card.type) {
		case 'album':
			subtitle = (card as Album).artists.map(a => a.name).join(', ');
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
<a {href}
	 class="group card preset-filled-surface-100-900 border-[1px] border-surface-200-800 card-hover divide-surface-200-800 block max-w-md divide-y ">
	<header>
		<img src={card?.images?.at(0)?.url}
				 class="rounded-lg object-cover h-full"
				 width="100%"
				 alt={subtitle}
				 loading="lazy" />
	</header>
	<article class="group-hover:block hidden absolute bg-surface-100-900 space-y-4 p-4 border-[1px] border-surface-200-800 rounded-md w-full ">
		<h2 class="text-lg">
			{title}
		</h2>
		<h3 class="h5 ">
			{subtitle || ""}
		</h3>
	</article>
	<article class="space-y-4 p-4">
		<h2 class="text-lg text-nowrap whitespace-nowrap overflow-hidden">{title}</h2>
		<h3 class="h5 text-nowrap whitespace-nowrap overflow-hidden">{subtitle || ""}</h3>
	</article>
</a>
