import adapter from '@sveltejs/adapter-auto';
import { vitePreprocess } from '@sveltejs/kit/vite';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	kit: {
		adapter: adapter(),
		typescript: {
			config: (c) => { c.compilerOptions.baseUrl = "."; return c; }
		}
	},
	preprocess: [vitePreprocess()]
};

export default config;
