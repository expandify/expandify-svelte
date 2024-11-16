<script lang="ts">
	import { dependencies } from '$lib/stores/dependencies';
	import CoverFlow from '$lib/components/CoverFlow.svelte';

	dependencies.onlyAlbumsNeeded();

</script>

<div class="flex flex-col justify-center items-center gap-52">
	<h2>Old Apple Cover Flow Recreation</h2>
	<CoverFlow></CoverFlow>
</div>

