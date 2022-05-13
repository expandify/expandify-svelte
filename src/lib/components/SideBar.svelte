<script>
  import {user} from "../../stores/user.js";

  function logout() {
   localStorage.clear()
  }
</script>

<aside class="has-background-grey-dark section menu is-flex is-flex-direction-column fixed vh-100">

  <a href="/" class="is-size-4">Exportify</a>

  <hr class="is-size-6 has-background-grey-lighter">

  <p class="menu-label is-size-6">Library</p>

  <ul class="menu-list is-size-6">
    <li><a href="/library/albums">Albums</a></li>
    <li><a href="/library/artists">Artist</a></li>
    <li><a href="/library/libraries">Libraries</a></li>
    <li><a href="/library/playlists">Playlists</a></li>
    <li><a href="/library/songs">Songs</a></li>
    <li><a href="/library/user">User</a></li>
  </ul>

  <hr class="mt-auto is-size-6 has-background-grey-lighter">

  <select data-choose-theme>
    <option value="light">Light</option>
    <option value="dark">Dark</option>
    <option value="night">Night</option>
  </select>
  
  <div class="dropdown is-up is-hoverable">
    <button class="button overflow" aria-haspopup="true" aria-controls="dropdown-user">
      <strong class="overflow">{$user.user.display_name}</strong>
    </button>
    <div class="dropdown-menu" id="dropdown-user" role="menu">
      <div class="dropdown-content">
        <a class="dropdown-item" href="/">Settings</a>
        <a class="dropdown-item" href="/library/user">Profile</a>
        <hr class="dropdown-divider">
        <a class="dropdown-item" on:click="{logout}" href="/api/auth/logout">Sign out</a>
      </div>
    </div>

  </div>
</aside>

<style lang="scss">
  .fixed {
    top: 0;
    position: sticky;
  }
  .overflow {
    text-overflow: ellipsis;
    overflow: hidden;
  }
  .overflow:hover {
    overflow: visible;
  }
</style>
