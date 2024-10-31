<script lang="ts">
    let {
        class: className = "",
        skeleton = false,
        length = "5rem",
        children
    } = $props<{
        class?: string;
        skeleton?: boolean;
        length?: string;
        children?: import('svelte').Snippet;
    }>();

</script>

{#if !skeleton}
    <span class={className}>{@render children?.()}</span>
{:else }
    <div class="{className} skeleton" style="width: {length}"></div>
{/if}


<style lang="scss">

  .skeleton {
    background-color: var(--background-elevated-highlight);
    height: 1rem;
    border-radius: 10rem;
    margin: 0.1rem 0;
  }
</style>