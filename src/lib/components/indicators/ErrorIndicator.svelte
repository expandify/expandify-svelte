<script lang="ts">
	import Indicator from "./Indicator.svelte";

	export let message: string;
</script>


<Indicator color="var(--text-negative)">
	<span>{message}</span>
</Indicator>


<style lang="scss">
</style>
