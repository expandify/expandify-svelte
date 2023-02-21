<script lang="ts">
	import '../app.scss';
	import { startAutoRefresh } from '$lib/auth/auth';
	import FloatingNotifications from '$lib/components/notifications/FloatingNotifications.svelte';

	startAutoRefresh();
</script>

<div class="page">
	<slot />
	<FloatingNotifications />
</div>


<style lang="scss">	
	.page {			
		min-height: 100vh;
	}
</style>
	