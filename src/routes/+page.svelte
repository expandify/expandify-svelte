<script lang="ts">
    import {browser} from '$app/environment';
    import {goto} from '$app/navigation';
    import Button from '$lib/components/common/Button.svelte';
    import {spotifySession} from '$lib/stores/spotifySession';


    if (browser && $spotifySession) {
        goto("/dashboard");
    }
</script>

<main class="min-h-lvh flex flex-col justify-center items-center ">
    <h1>Welcome to Expandify</h1>
    <Button click={() => goto("/auth")} text="Login With Spotify" type="spotify"></Button>
</main>