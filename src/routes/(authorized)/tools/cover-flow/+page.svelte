<script lang="ts">
	import { dependencies } from '$lib/stores/dependencies';
	import CoverFlow from '$lib/components/CoverFlow.svelte';
	import { Heading } from 'flowbite-svelte';


	dependencies.onlyAlbumsNeeded();

	$: coverflowContainerWidth = 1000;

</script>

<div bind:clientWidth={coverflowContainerWidth} class="flex flex-col justify-center items-center gap-52">
	<Heading>Old Apple Cover Flow Recreation</Heading>
	<CoverFlow bind:width="{coverflowContainerWidth}"></CoverFlow>
</div>

