<script lang="ts">
	import IconLink from "$lib/components/common/IconLink.svelte";
  import NavBarSection from "./NavBarSection.svelte";
  import {DownloadIcon, MusicIcon } from 'svelte-feather-icons';
</script>


<NavBarSection title="Tools">
  <IconLink icon={DownloadIcon} href="/tools/release-tracker">Release Tracker</IconLink>
  <IconLink icon={MusicIcon} href="/tools/song-finder">Song Finder</IconLink>
  <IconLink icon={MusicIcon} href="/tools/library-value">Library Value</IconLink>
  <IconLink icon={DownloadIcon} href="/tools/backup">Backup</IconLink>  
</NavBarSection>