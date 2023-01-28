<script lang="ts">
	import { StoreState } from "$lib/stores/types";
	import ErrorIndicator from "./ErrorIndicator.svelte";
	import LoadingIndicator from "./LoadingIndicator.svelte";
	import SuccessIndicator from "./SuccessIndicator.svelte";
	
	export let state: StoreState;
	export let successMsg: string;
	export let errorMsg: string;
	export let loadingMsg: string;
	export let max: number | null;
	export let value: number | null;
</script>

{#if state === StoreState.Ready}
<SuccessIndicator message={successMsg}/>

{:else if state === StoreState.Error}
<ErrorIndicator message={errorMsg}/>

{:else if state === StoreState.Loading}
<LoadingIndicator
  max={max}
  value={value}
  message={loadingMsg}/>
{/if}