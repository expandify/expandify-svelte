<script lang="ts">
	import IconLink from "$lib/components/common/IconLink.svelte";
  import NavBarSection from "./NavBarSection.svelte";
  import {DiscIcon, UsersIcon, MusicIcon } from 'svelte-feather-icons';
</script>


<NavBarSection title="Information">
  <IconLink icon={DiscIcon} href="/information/album">Albums</IconLink>
  <IconLink icon={UsersIcon} href="/information/artist">Artists</IconLink>
  <IconLink icon={MusicIcon} href="/information/track">Tracks</IconLink>	
</NavBarSection>