<script module lang="ts">
	import { defineMeta } from '@storybook/addon-svelte-csf';
	import Card from './Card.svelte';
	import type { Album, Artist } from '$lib/types/spotify';

	// More on how to set up stories at: https://storybook.js.org/docs/writing-stories
	const { Story } = defineMeta({
		title: 'Example/Card',
		component: Card,
		tags: ['autodocs'],
		argTypes: {
			card: {
				control: { type: 'select' },
				options: [
					{
						name: 'Album',
						artists: [{name: "artist 1"}, {name: "artist 2"}],
					} as Album
				]
			}
		}
	});

	const artist: Artist = {
		name: "Artist 1",
		external_urls: { spotify: "" },
		followers: { total: 0, href: "" },
		genres: [],
		href: '',
		id: '',
		images: [],
		popularity: 0,
		type: 'artist',
		uri: ''

	}
</script>

<!-- More on writing stories with args: https://storybook.js.org/docs/writing-stories/args -->
<Story name="Primary" args={{ card: artist }} />
