import type { Config } from 'tailwindcss';
import tailwindcssAnimate from 'tailwindcss-animate';
import { skeleton, contentPath } from '@skeletonlabs/skeleton/plugin';
import * as themes from '@skeletonlabs/skeleton/themes';

const config: Config = {
	darkMode: ['class'],
	content: [
		"./index.html",
		"./src/**/*.{html,js,ts,svelte}",
		contentPath(import.meta.url, "svelte"),
	],
	//safelist: ['dark'],
	theme: {
		extend: {}
	},
	plugins: [
		tailwindcssAnimate,
		skeleton({
			// NOTE: each theme included will increase the size of your CSS bundle
			themes: [themes.wintry],
		}),
	]
};

export default config;
