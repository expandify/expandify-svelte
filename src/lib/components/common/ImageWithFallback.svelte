<script lang="ts">
    import type {AlbumSimplified, Artist, PlaylistSimplified} from "$lib/types/spotify";
    import Svg from "./Svg.svelte";

    let {
        type,
        fallbackSvg = type?.type || "",
        borderRadius = type?.type === "artist" ? "100%" : "1rem",
        showFallback = false
    } = $props<{
        type: AlbumSimplified | Artist | PlaylistSimplified | null | undefined;
        fallbackSvg?: string;
        borderRadius?: string;
        showFallback?: boolean;
    }>();

    let image = type?.images?.at(0)?.url;
    let imageAlt = type?.name;


</script>


<div class="picture">
    {#if !showFallback && image}
        <img src={image}
             class="image"
             alt={imageAlt}
             loading="lazy"
             style="border-radius: {borderRadius};"/>
    {:else}
        <div class="fallback" style="border-radius: {borderRadius};">
            <Svg name={fallbackSvg} width={"auto"} height={"auto"} class="svg"></Svg>
        </div>
    {/if}
</div>


<style lang="scss">

  .picture {

    aspect-ratio: 1/1;
    height: 100%;
    box-shadow: 0 0 0.5rem 0.1rem var(--background-base);

    .fallback {
      background-color: var(--background-elevated-highlight);

      position: relative;
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 100%;

      :global(.svg) {
        width: 40%;
        height: 40%;
      }

    }

    .image {
      max-width: 100%;
      max-height: 100%;
      object-fit: cover;
      aspect-ratio: 1/1;
    }
  }

</style>