<script lang="ts">
    import '../app.css';
    import {startAutoRefresh} from '$lib/auth/auth';
    import Notifications from '$lib/components/layout/Notifications.svelte';
    import {browser} from '$app/environment';
    import {spotifySession, type SpotifySession} from '$lib/stores/spotifySession';
		import { ModeWatcher } from 'mode-watcher';

    let { children } = $props<{
			children?: import('svelte').Snippet;
		}>();

    if (browser) {
        try {
            const data: SpotifySession = JSON.parse(localStorage.spotify);
            spotifySession.set({...data, expirationDate: new Date(data.expirationDate)});
        } catch (_) {
            spotifySession.set(null);
        }
        spotifySession.subscribe((value) => (localStorage.spotify = JSON.stringify(value)));
    }

    startAutoRefresh();
</script>

<ModeWatcher></ModeWatcher>
<div class="min-h-lvh">
	{@render children?.()}
	<Notifications></Notifications>
</div>
