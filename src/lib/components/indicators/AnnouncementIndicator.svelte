<script lang="ts">
	import Indicator from "./Indicator.svelte";

	export let message: string;
</script>


<Indicator color="var(--text-announcement)">
	<span>{message}</span>
</Indicator>


<style lang="scss">
</style>
