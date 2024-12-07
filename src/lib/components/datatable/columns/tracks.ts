import type { ColumnDef } from "@tanstack/table-core";
import type { SavedTrack } from '$lib/types/spotify';
import { renderComponent } from '$lib/components/ui/data-table';
import DataTableCheckbox from '$lib/components/datatable/DataTableCheckbox.svelte';
import DataTableActions from '$lib/components/datatable/DataTableAction.svelte';
import { formateDate, msToTime } from '$lib/utils/converter/date-time';



export const savedTrackColumns: ColumnDef<SavedTrack>[] = [
	{
		id: "select",
		header: ({ table }) =>
			renderComponent(DataTableCheckbox, {
				checked: table.getIsAllPageRowsSelected(),
				indeterminate:
					table.getIsSomePageRowsSelected() &&
					!table.getIsAllPageRowsSelected(),
				onCheckedChange: (value) => table.toggleAllPageRowsSelected(!!value),
				"aria-label": "Select all"
			}),
		cell: ({ row }) =>
			renderComponent(DataTableCheckbox, {
				checked: row.getIsSelected(),
				onCheckedChange: (value) => row.toggleSelected(!!value),
				"aria-label": "Select row"
			}),
		enableSorting: false,
		enableHiding: false
	},
	{
		accessorKey: "name",
		header: "Name",
	},
	{
		accessorKey: "artists",
		header: "Artists",
		cell: ({ row }) => row.original.artists.map(a => a.name).join(", ")
	},
	{
		accessorKey: "album",
		header: "Album",
		cell: ({ row }) => row.original.album.name
	},
	{
		accessorKey: "duration_ms",
		header: "Duration",
		cell: ({ row }) => msToTime(row.original.duration_ms)
	},
	{
		accessorKey: "disc_number",
		header: "Disc Number",
	},
	{
		accessorKey: "explicit",
		header: "Explicit",
	},
	{
		accessorKey: "available_markets",
		header: "Available Markets",
		cell: ({ row }) => row.original.available_markets?.join(", ")
	},
	{
		accessorKey: "external_urls",
		header: "External Urls",
		cell: ({row}) => row.original.external_urls.spotify
	},
	{
		accessorKey: "href",
		header: "Link",
	},
	{
		accessorKey: "id",
		header: "ID",
	},
	{
		accessorKey: "is_playable",
		header: "Is Playable",
	},
	{
		accessorKey: "linked_from",
		header: "Linked From",
	},
	{
		accessorKey: "preview_url",
		header: "Preview Url",
	},
	{
		accessorKey: "track_number",
		header: "Track Number",
	},
	{
		accessorKey: "type",
		header: "Type",
	},
	{
		accessorKey: "uri",
		header: "URI",
	},
	{
		id: "ean",
		accessorKey: "external_ids",
		header: "EAN",
		cell: ({ row }) => row.original.external_ids.ean
	},
	{
		id: "upc",
		accessorKey: "external_ids",
		header: "UPC",
		cell: ({ row }) => row.original.external_ids.upc
	},
	{
		id: "isrc",
		accessorKey: "external_ids",
		header: "ISRC",
		cell: ({ row }) => row.original.external_ids.isrc
	},
	{
		accessorKey: "popularity",
		header: "Popularity",
	},
	{
		accessorKey: "added_at",
		header: "Added At",
		cell: ({ row }) => formateDate(row.original.added_at)
	},
	{
		id: "actions",
		enableHiding: false,
		cell: ({ row }) =>
			renderComponent(DataTableActions, { id: row.original.id })
	}
];