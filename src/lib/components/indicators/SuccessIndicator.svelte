<script lang="ts">
	import Indicator from "./Indicator.svelte";

	export let message: string;

	const TIMEOUT = 8000;
</script>


<Indicator color="var(--text-positive)" timeout={TIMEOUT}>
	<span>{message}</span>
</Indicator>


<style lang="scss">
</style>
