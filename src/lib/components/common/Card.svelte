<script lang="ts">
	import * as Card from "$lib/components/ui/card/index.js";
	import * as Tooltip from "$lib/components/ui/tooltip/index.js";
	import type { Album, Artist, Playlist } from '$lib/types/spotify';
	import { AspectRatio } from "$lib/components/ui/aspect-ratio/index.js";

	let { card } = $props<{
		card: Album | Artist | Playlist;
	}>();

	let title = card.name;
	let subtitle = $state<string>();
	let href = $state<string | null>();

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
<a href={href}>
<Card.Root class="hover:bg-accent hover:text-accent-foreground hover:cursor-pointer"
onclick={() => console.log('clicked')}>
	<Card.Header>
		<Tooltip.Provider>
			<Tooltip.Root>
				<Tooltip.Trigger >
					<Card.Title class="w-full overflow-hidden whitespace-nowrap overflow-ellipsis">
						{title}
					</Card.Title>
					<Card.Description class="w-full overflow-hidden whitespace-nowrap overflow-ellipsis">
						{subtitle || ""}
					</Card.Description>
				</Tooltip.Trigger>
				<Tooltip.Content class="w-56">
					<Card.Title>
						{title}
					</Card.Title>
					<Card.Description>
						{subtitle || ""}
					</Card.Description>
				</Tooltip.Content>
			</Tooltip.Root>
		</Tooltip.Provider>
	</Card.Header>
	<Card.Content>
		<AspectRatio ratio={1}>
		<img src={card?.images?.at(0)?.url}
				 class="rounded-lg object-cover h-full"
				 width="100%"
				 alt={subtitle}
				 loading="lazy"
				 />
		</AspectRatio>
	</Card.Content>
</Card.Root>
</a>
