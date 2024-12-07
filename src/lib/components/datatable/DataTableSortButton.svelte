<script lang="ts">
	import type { ComponentProps } from 'svelte';
	import ArrowUpDown from 'lucide-svelte/icons/arrow-up-down';
	import { Button } from '$lib/components/ui/button/index';
	import type { Column } from '@tanstack/table-core';
	import { ArrowDown, ArrowUp } from 'lucide-svelte';

	let { column, title, variant = 'ghost', ...restProps }:
		ComponentProps<typeof Button> & {
		column: Column<unknown>,
		title: string
	} = $props();
</script>

<Button {variant} {...restProps}>
	{title}
	{#if column.getIsSorted() === 'asc'}
		<ArrowUp class="ml-2 size-4" />
	{:else if column.getIsSorted() === 'desc'}
		<ArrowDown class="ml-2 size-4" />
	{:else }
		<ArrowUpDown class="ml-2 size-4" />
	{/if}

</Button>