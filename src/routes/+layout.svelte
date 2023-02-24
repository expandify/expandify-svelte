<script lang="ts">
	import '../app.scss';
	import { startAutoRefresh } from '$lib/auth/auth';
	import FloatingNotifications from '$lib/components/notifications/FloatingNotifications.svelte';
	import { browser } from '$app/environment';
	import { spotifySession, type SpotifySession } from '$lib/stores/spotifySession';


	if (browser) {
		try {
			const data: SpotifySession = JSON.parse(localStorage.spotify);
			spotifySession.set({...data, expirationDate: new Date(data.expirationDate)});
		} catch (error) {
			spotifySession.set(null);
		}
		spotifySession.subscribe((value) => (localStorage.spotify = JSON.stringify(value)));
	}

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
	