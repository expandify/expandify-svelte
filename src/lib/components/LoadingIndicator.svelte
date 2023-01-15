<script lang="ts">
	export let message: string;
	export let value: number | null = null;
	export let max: number | null = null;
	export let error: boolean = false;
</script>

<article class="floating" class:error>
	<h6 class="header">{message}</h6>

	{#if !error}
		{#if value && max}
			<progress {value} {max} />
		{:else}
			<progress />
		{/if}
	{/if}
</article>

<style lang="scss">
	.error {
		outline: 0.1rem solid var(--form-element-invalid-border-color);
	}

	.floating {
		margin: 1rem;
		padding: 0.5rem 1rem;

		.header {
			display: flex;
			align-items: center;
			justify-content: center;
			margin-bottom: 0.5rem;
		}
		progress {
			margin: 0;
		}
	}
</style>
