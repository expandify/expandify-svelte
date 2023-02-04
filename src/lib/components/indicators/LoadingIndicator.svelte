<script lang="ts">
	import LoadingDots from "../loading/LoadingDots.svelte";
import ProgressBar from "../loading/ProgressBar.svelte";
import Indicator from "./Indicator.svelte";

	export let message: string;
	export let value: number | null = null;
	export let max: number | null = null;

</script>


<Indicator alwaysVisible={true}>
	<div class="content">
		{#if value && max}
		<span>{message}</span>		
		<ProgressBar {max} {value} />
		{:else}
		<LoadingDots message={message} />
		{/if}
		
	</div>
</Indicator>


<style lang="scss">
	.content {
		display: flex;
		flex-direction: column;
		gap: 0.5rem;

	}
</style>
