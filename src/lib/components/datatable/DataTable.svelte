<script lang="ts" generics="TValue">
	import ChevronDown from 'lucide-svelte/icons/chevron-down';
	import {
		type PaginationState,
		type RowSelectionState,
		type SortingState,
		type VisibilityState,
		getCoreRowModel,
		getFilteredRowModel,
		getPaginationRowModel,
		getSortedRowModel, type GlobalFilterTableState, type Row
	} from '@tanstack/table-core';
	import {
		DropdownMenu,
		DropdownMenuCheckboxItem,
		DropdownMenuContent,
		DropdownMenuTrigger
	} from '$lib/components/ui/dropdown-menu';
	import { Button } from '$lib/components/ui/button';
	import { Input } from '$lib/components/ui/input';
	import { FlexRender, createSvelteTable } from '$lib/components/ui/data-table';
	import { buildColumns, type ColumnConfig } from '$lib/components/datatable/columns/columns';
	import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '$lib/components/ui/table';

	let { data, columConfig, filterFunc }: {
		data: TValue[],
		columConfig: ColumnConfig<TValue>,
		filterFunc: (row: Row<TValue>, filterValue: string) => boolean,
	} = $props();

	let pagination = $state<PaginationState>({ pageIndex: 0, pageSize: 15 });
	let sorting = $state<SortingState>([]);
	let globalFilter = $state<GlobalFilterTableState>();
	let rowSelection = $state<RowSelectionState>({});
	let columnVisibility = $state<VisibilityState>({
		'available_markets': false,
		'artists': false,
		'preview_url': false,
		'uri': false,
		'href': false,
		'external_urls': false
	});

	const columns = buildColumns(columConfig);

	const table = createSvelteTable({
		get data() {
			return data;
		},
		enableSorting: true,
		enableGlobalFilter: true,
		columns: columns,
		state: {
			get pagination() {
				return pagination;
			},
			get sorting() {
				return sorting;
			},
			get columnVisibility() {
				return columnVisibility;
			},
			get rowSelection() {
				return rowSelection;
			},
			get globalFilter() {
				return globalFilter;
			}

		},
		getCoreRowModel: getCoreRowModel(),
		getPaginationRowModel: getPaginationRowModel(),
		getSortedRowModel: getSortedRowModel(),
		getFilteredRowModel: getFilteredRowModel(),
		globalFilterFn: (row, _, filterValue) => filterFunc(row, filterValue),
		onPaginationChange: (updater) => {
			if (typeof updater === 'function') {
				pagination = updater(pagination);
			} else {
				pagination = updater;
			}
		},
		onSortingChange: (updater) => {
			if (typeof updater === 'function') {
				sorting = updater(sorting);
			} else {
				sorting = updater;
			}
		},
		onGlobalFilterChange: (updater) => {
			if (typeof updater === 'function') {
				globalFilter = updater(globalFilter);
			} else {
				globalFilter = updater;
			}
		},
		onColumnVisibilityChange: (updater) => {
			if (typeof updater === 'function') {
				columnVisibility = updater(columnVisibility);
			} else {
				columnVisibility = updater;
			}
			console.log(columnVisibility);
		},
		onRowSelectionChange: (updater) => {
			if (typeof updater === 'function') {
				rowSelection = updater(rowSelection);
			} else {
				rowSelection = updater;
			}
		}
	});
</script>

<div class="w-full">
	<div class="flex items-center py-4">
		<Input
			placeholder="Filter emails..."
			value={table.getState().globalFilter}
			oninput={(e) => table.setGlobalFilter(e.currentTarget.value)}
			onchange={(e) => table.setGlobalFilter(e.currentTarget.value)}
			class="max-w-sm"
		/>
		<DropdownMenu>
			<DropdownMenuTrigger>
				{#snippet child({ props })}
					<Button {...props} variant="outline" class="ml-auto">
						Columns
						<ChevronDown class="ml-2 size-4" />
					</Button>
				{/snippet}
			</DropdownMenuTrigger>
			<DropdownMenuContent align="end">
				{#each table
					.getAllColumns()
					.filter((col) => col.getCanHide()) as column}
					<DropdownMenuCheckboxItem
						class="capitalize"
						controlledChecked
						checked={column.getIsVisible()}
						onCheckedChange={(value) => column.toggleVisibility(!!value)}
					>
						{column.id}
					</DropdownMenuCheckboxItem>
				{/each}
			</DropdownMenuContent>
		</DropdownMenu>
	</div>
	<div class=" ">
		<div class="rounded-md border ">
			<Table>
				<TableHeader>
					{#each table.getHeaderGroups() as headerGroup (headerGroup.id)}
						<TableRow>
							{#each headerGroup.headers as header (header.id)}
								<TableHead class="[&:has([role=checkbox])]:pl-3">
									{#if !header.isPlaceholder}
										<FlexRender
											content={header.column.columnDef.header}
											context={header.getContext()}
										/>
									{/if}
								</TableHead>
							{/each}
						</TableRow>
					{/each}
				</TableHeader>
				<TableBody>
					{#each table.getRowModel().rows as row (row.id)}
						<TableRow data-state={row.getIsSelected() && "selected"}>
							{#each row.getVisibleCells() as cell (cell.id)}
								<TableCell class="[&:has([role=checkbox])]:pl-3">
									<FlexRender
										content={cell.column.columnDef.cell}
										context={cell.getContext()}
									/>
								</TableCell>
							{/each}
						</TableRow>
					{:else}
						<TableRow>
							<TableCell colspan={columns.length} class="h-24 text-center">
								No results.
							</TableCell>
						</TableRow>
					{/each}
				</TableBody>
			</Table>
		</div>
		<div class="flex items-center justify-end space-x-2 pt-4">
			<div class="text-muted-foreground flex-1 text-sm">
				{table.getFilteredSelectedRowModel().rows.length} of
				{table.getFilteredRowModel().rows.length} row(s) selected.
			</div>
			<div class="space-x-2">
				<Button
					variant="outline"
					size="sm"
					onclick={() => table.previousPage()}
					disabled={!table.getCanPreviousPage()}
				>
					Previous
				</Button>
				<Button
					variant="outline"
					size="sm"
					onclick={() => table.nextPage()}
					disabled={!table.getCanNextPage()}
				>
					Next
				</Button>
			</div>
		</div>
	</div>
</div>