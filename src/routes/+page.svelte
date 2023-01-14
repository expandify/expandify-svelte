<script>
  import { authorizeUrl } from "$lib/auth/authorize";
	import { page } from '$app/stores'; 
  import { authenticate } from '$lib/auth/authorize'
  import { browser } from '$app/environment';
	import { goto } from '$app/navigation';
	import { clearAuthData } from "$lib/stores/authData";
	import { state } from "$lib/stores/state";
	import { Cache } from "$lib/stores/cache";
	import { error } from "@sveltejs/kit";

  if (browser) { getToken();}

  async function getToken() {
    const code = $page.url.searchParams.get("code");
    await authenticate(code);     
    await goto("/");
  }

  async function authorize() {
    await goto(await authorizeUrl())
  }

  function logout() {
    clearAuthData();
    Cache.clearAll();
  }

  function gf() {
    throw error(404, {
      message: "qweqwe"
    })
  }

</script>

<h1>Welcome to Expandify</h1>

{#if !$state.authenticated}  
  <button on:click={authorize}>Login with Spotify</button>  
{:else}
  <button on:click={logout}>Logout</button>  
{/if}

<button on:click={gf}>Throw Error</button>  






