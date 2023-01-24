<script lang="ts">
	import '../app.scss';
	import FloatingCacheIndicators from '$lib/components/layout/FloatingCacheIndicators.svelte';
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
