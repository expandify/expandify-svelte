<script lang="ts">
    import {browser} from '$app/environment';
    import {goto} from '$app/navigation';

    import {spotifySession} from '$lib/stores/spotifySession';
    import { Button } from '$lib/components/ui/button';


    if (browser && $spotifySession) {
        goto("/dashboard");
    }
</script>

<main class="min-h-lvh flex flex-col justify-center items-center ">
    <h1>Welcome to Expandify</h1>
    <Button variant="destructive" onclick={() => goto("/auth")}>Login With Spotify</Button>
</main>