<script lang="ts">
	import IconLink from "$lib/components/common/IconLink.svelte";
  import NavBarSection from "./NavBarSection.svelte";
  import {DownloadIcon, MusicIcon } from 'svelte-feather-icons';
</script>


<NavBarSection title="Tools">
  <IconLink icon={MusicIcon} href="/tools/song-finder">Song Finder</IconLink>
  <IconLink icon={DownloadIcon} href="/tools/backup">Backup</IconLink>
</NavBarSection>