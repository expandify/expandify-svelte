<script lang="ts">
	import '../app.scss';
	import FloatingCacheIndicators from '$lib/components/indicators/FloatingCacheIndicators.svelte';
	import { startAutoRefresh } from '$lib/auth/auth';

	startAutoRefresh();
</script>

<div class="page">
	<slot />
	<FloatingCacheIndicators></FloatingCacheIndicators>	
</div>


<style lang="scss">	
	.page {			
		min-height: 100vh;
	}
</style>
	