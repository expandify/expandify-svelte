import type { CellContext, Column, ColumnDef, ColumnDefTemplate } from '@tanstack/table-core';
import { renderComponent } from '$lib/components/ui/data-table';
import DataTableSortButton from '$lib/components/datatable/DataTableSortButton.svelte';
import DataTableCheckbox from '$lib/components/datatable/DataTableCheckbox.svelte';
import DataTableActions from '$lib/components/datatable/DataTableAction.svelte';

export type ColumnConfig<T> = {
	enableSelect: boolean;
	enableAction: boolean;
	columns: {
		id?: string;
		accessorKey: string;
		title: string;
		cell?: ColumnDefTemplate<CellContext<T, unknown>>;
		disableSort?: boolean;
		excludeInSearch?: boolean;
	}[];
};

export function buildColumns<T>(columnConf: ColumnConfig<T>): ColumnDef<T>[] {
	const columnDef: ColumnDef<T>[] = columnConf.columns.map((c) => ({
		...(c.id ? { id: c.id } : {}),
		accessorKey: c.accessorKey,
		header: ({ column }) => {
			if (c.disableSort) {
				return c.title;
			}
			return renderComponent(DataTableSortButton, {
				onclick: () => column.toggleSorting(),
				title: c.title,
				column: column as Column<unknown>
			});
		},
		...(c.cell ? { cell: c.cell } : {}),
		enableSorting: !c.disableSort,
		enableGlobalFilter: !c.excludeInSearch
	}));
	if (columnConf.enableSelect) {
		columnDef.unshift({
			id: 'select',
			header: ({ table }) =>
				renderComponent(DataTableCheckbox, {
					checked: table.getIsAllPageRowsSelected(),
					indeterminate: table.getIsSomePageRowsSelected() && !table.getIsAllPageRowsSelected(),
					onCheckedChange: (value) => table.toggleAllPageRowsSelected(!!value),
					'aria-label': 'Select all'
				}),
			cell: ({ row }) =>
				renderComponent(DataTableCheckbox, {
					checked: row.getIsSelected(),
					onCheckedChange: (value) => row.toggleSelected(!!value),
					'aria-label': 'Select row'
				}),
			enableSorting: false,
			enableHiding: false
		});

		if (columnConf.enableAction) {
			columnDef.push({
				id: 'actions',
				enableHiding: false,
				cell: ({ row }) => renderComponent(DataTableActions, { id: 'TODO' })
			});
		}
	}
	return columnDef;
}
