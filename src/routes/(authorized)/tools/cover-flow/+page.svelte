<script lang="ts">
	import { dependencies } from '$lib/stores/dependencies';
	import CoverFlow from '$lib/components/CoverFlow.svelte';


	dependencies.onlyAlbumsNeeded();

	let coverflowContainerWidth = $state(1000);
	

</script>

<div bind:clientWidth={coverflowContainerWidth} class="flex flex-col justify-center items-center gap-52">
	<h2>Old Apple Cover Flow Recreation</h2>
	<CoverFlow bind:width="{coverflowContainerWidth}"></CoverFlow>
</div>

