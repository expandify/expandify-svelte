import type { SavedTrack } from '$lib/types/spotify';
import { formateDate, msToTime } from '$lib/utils/converter/date-time';
import type { ColumnConfig } from '$lib/components/datatable/columns/columns';
import type { Row } from '@tanstack/table-core';

export function savedTracksFilterFunc(row: Row<SavedTrack>, filterValue: string): boolean {
	const lowerFilterValue = filterValue.toLowerCase();
	const track = row.original;

	const name = track.name.toLowerCase();
	const artists = track.artists.map(a => a.name).join().toLowerCase();
	const album = track.album.name.toLowerCase();

	return (name + album + artists).includes(lowerFilterValue);
}

export const savedTrackColumns: ColumnConfig<SavedTrack> = {
	enableSelect: true,
	enableAction: true,
	columns: [
		{ accessorKey: 'name', title: 'Name' },
		{
			accessorKey: 'artists',
			title: 'Artists',
			cell: ({ row }) => row.original.artists.map((a) => a.name).join(', ')
		},
		{
			accessorKey: 'album',
			title: 'Album',
			cell: ({ row }) => row.original.album.name
		},
		{
			accessorKey: 'duration_ms',
			title: 'Duration',
			cell: ({ row }) => msToTime(row.original.duration_ms)
		},
		{ accessorKey: 'disc_number', title: 'Disc Number' },
		{ accessorKey: 'explicit', title: 'Explicit' },
		{
			accessorKey: 'available_markets',
			title: 'Available Markets',
			cell: ({ row }) => row.original.available_markets?.join(', ')
		},
		{
			accessorKey: 'external_urls',
			title: 'External Urls',
			cell: ({ row }) => row.original.external_urls.spotify
		},
		{ accessorKey: 'href', title: 'Link' },
		{ accessorKey: 'id', title: 'ID' },
		{ accessorKey: 'is_playable', title: 'Is Playable' },
		{ accessorKey: 'linked_from', title: 'Linked From' },
		{ accessorKey: 'preview_url', title: 'Preview Url' },
		{ accessorKey: 'track_number', title: 'Track Number' },
		{ accessorKey: 'type', title: 'Type' },
		{ accessorKey: 'uri', title: 'URI' },
		{
			id: 'ean',
			accessorKey: 'external_ids',
			title: 'EAN',
			cell: ({ row }) => row.original.external_ids.ean
		},
		{
			id: 'upc',
			accessorKey: 'external_ids',
			title: 'UPC',
			cell: ({ row }) => row.original.external_ids.upc
		},
		{
			id: 'isrc',
			accessorKey: 'external_ids',
			title: 'ISRC',
			cell: ({ row }) => row.original.external_ids.isrc
		},
		{ accessorKey: 'popularity', title: 'Popularity' },
		{
			accessorKey: 'added_at',
			title: 'Added At',
			cell: ({ row }) => formateDate(row.original.added_at)
		}
	]
}
