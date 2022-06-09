<script>
  import {SearchIcon} from "svelte-feather-icons";

  export let search
  export let searchIn
</script>


<div class="search">
  <SearchIcon class="icon"/>
  <input bind:value={search} placeholder="Search in {searchIn}">
</div>

<style lang="scss">

  .search {
    background-color: var(--bg-main-100);
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 2rem;
    border-radius: 0.2rem;
    padding: 0.2rem;

    input {
      box-sizing: border-box;
      border: none;
      background-color: var(--bg-main-100);
      color: var(--text-700);
      margin-left: 0.4rem;
    }
    input:focus {
      outline: none;
    }
  }

</style>