<script>
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon, UserIcon, FileTextIcon} from 'svelte-feather-icons'
  import MenuEntry from "./MenuEntry.svelte";

  export let items = []
</script>

<ul class="menu-list">
  {#each items as item}
    <li>
      <MenuEntry href={item.href} icon={item.icon}>
        {item.text}
      </MenuEntry>
    </li>
  {/each}
</ul>


<style lang="scss">

  ul.menu-list {

    margin-top: 2rem;
    padding: 0;
    font-weight: bold;

    li {
      list-style-type: none;
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      padding-left: 1rem;
      height: 2rem;
      margin-bottom: 0.6rem;
    }
  }

</style>