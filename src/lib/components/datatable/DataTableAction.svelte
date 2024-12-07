<script lang="ts">
	import Ellipsis from "lucide-svelte/icons/ellipsis";
	import { Button } from "$lib/components/ui/button/index.js";
	import {
		DropdownMenu, DropdownMenuContent, DropdownMenuGroupHeading,
		DropdownMenuItem,
		DropdownMenuSeparator,
		DropdownMenuTrigger,
		DropdownMenuGroup
	} from '$lib/components/ui/dropdown-menu/index';

	let { id }: { id: string } = $props();
</script>

<DropdownMenu>
	<DropdownMenuTrigger>
		{#snippet child({ props })}
			<Button
				{...props}
				variant="ghost"
				size="icon"
				class="relative size-8 p-0"
			>
				<span class="sr-only">Open menu</span>
				<Ellipsis class="size-4" />
			</Button>
		{/snippet}
	</DropdownMenuTrigger>
	<DropdownMenuContent>
		<DropdownMenuGroup>
			<DropdownMenuGroupHeading>Actions</DropdownMenuGroupHeading>
			<DropdownMenuItem onclick={() => navigator.clipboard.writeText(id)}>
				Copy payment ID
			</DropdownMenuItem>
		</DropdownMenuGroup>
		<DropdownMenuSeparator />
		<DropdownMenuItem>View customer</DropdownMenuItem>
		<DropdownMenuItem>View payment details</DropdownMenuItem>
	</DropdownMenuContent>
</DropdownMenu>