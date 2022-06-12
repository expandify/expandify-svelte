<script lang="ts">
  import {SearchIcon} from "svelte-feather-icons";

  export let search: string
  export let searchIn: string

  let isFocused = false;
  const onFocus =()=>isFocused=true;
  const onBlur =()=>isFocused=false;
</script>


<div class="search" class:focus={isFocused}>
  <SearchIcon class="icon"/>
  <input bind:value={search} placeholder="Search in {searchIn}" on:focus={onFocus} on:blur={onBlur}>
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
    border: 0.1rem solid rgba(0,0,0,0);
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

  .focus {
    border: 0.1rem solid var(--accent);
  }

</style>