<script lang="ts">
	import type { Album, Artist, Playlist } from '$lib/types/spotify';
	import { AspectRatio } from "$lib/components/ui/aspect-ratio/index.js";
	import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '$lib/components/ui/card/index.js';
	import { TooltipProvider, TooltipTrigger, Tooltip, TooltipContent } from '$lib/components/ui/tooltip/index.js';

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
<a href={href}>
<Card class="hover:bg-accent hover:text-accent-foreground hover:cursor-pointer"
			onclick={() => console.log('clicked')}>
	<CardHeader>
		<TooltipProvider>
			<Tooltip>
				<TooltipTrigger >
					<CardTitle class="w-full overflow-hidden whitespace-nowrap overflow-ellipsis">
						{title}
					</CardTitle>
					<CardDescription class="w-full overflow-hidden whitespace-nowrap overflow-ellipsis">
						{subtitle || ""}
					</CardDescription>
				</TooltipTrigger>
				<TooltipContent class="w-56">
					<CardTitle>
						{title}
					</CardTitle>
					<CardDescription>
						{subtitle || ""}
					</CardDescription>
				</TooltipContent>
			</Tooltip>
		</TooltipProvider>
	</CardHeader>
	<CardContent>
		<AspectRatio ratio={1}>
		<img src={card?.images?.at(0)?.url}
				 class="rounded-lg object-cover h-full"
				 width="100%"
				 alt={subtitle}
				 loading="lazy"
				 />
		</AspectRatio>
	</CardContent>
</Card>
</a>
