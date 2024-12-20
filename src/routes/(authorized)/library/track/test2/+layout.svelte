<script lang="ts">
	import { QueryClient, QueryClientProvider } from '@tanstack/svelte-query';

	const queryClient = new QueryClient();

	let { children } = $props<{
		children?: import('svelte').Snippet;
	}>();
</script>

<QueryClientProvider client={queryClient}>
	{@render children?.()}
</QueryClientProvider>
