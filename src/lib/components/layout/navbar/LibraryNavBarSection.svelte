<script lang="ts">
	import IconLink from "$lib/components/common/IconLink.svelte";
  import NavBarSection from "./NavBarSection.svelte";
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon } from 'svelte-feather-icons';
</script>


<NavBarSection title="Library">
  <IconLink icon={DiscIcon} href="/library/album">Albums</IconLink>
  <IconLink icon={UsersIcon} href="/library/artist">Artists</IconLink>
  <IconLink icon={ListIcon} href="/library/playlist">Playlists</IconLink>
  <IconLink icon={MusicIcon} href="/library/track">Tracks</IconLink>
</NavBarSection>