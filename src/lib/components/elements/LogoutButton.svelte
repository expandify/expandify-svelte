<script>
  import {LogOutIcon} from 'svelte-feather-icons'
  import MenuEntry from "./IconLink.svelte";

  import {goto} from "$app/navigation";

  function logout() {
    goto("/api/auth/logout")
  }
</script>


<div on:click={logout}>
  <MenuEntry icon={LogOutIcon}>Sign Out</MenuEntry>
</div>

