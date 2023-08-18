<script lang="ts">
    import ImageWithFallback from "$lib/components/common/ImageWithFallback.svelte";
    import type {Track} from "$lib/types/spotify";
    import {formateDate, msToTime} from "$lib/utils/converter/date-time";
    import {viewport} from "$lib/actions/useViewportAction";
    import SkeletonText from "$lib/components/common/SkeletonText.svelte";

    export let tracks: (Track & { added_at?: string })[];
    export let showImage = true;
    export let showAddedAt = false;

    let visible: Array<boolean> = Array(tracks.length).fill(true)

</script>

<div class="table">
    <div class="row title-row">
        <span class="position">#</span>
        {#if showImage}
            <div class="image-box">TITLE</div>
            <div class="title-box"></div>
        {:else}
            <div class="title-box">TITLE</div>
        {/if}
        <span class="album">ALBUM</span>
        {#if showAddedAt}
            <span class="date">DATE ADDED</span>
        {/if}
        <span class="time">TIME</span>
    </div>

    {#each tracks as track, i}
        <div class="row"
             use:viewport
             on:enterViewport={() => visible[i] = true}
             on:exitViewport={() => visible[i] = false}>

            <span class="position">{i + 1}</span>
            {#if showImage}
                <div class="image-box">
                    <ImageWithFallback type={track.album} fallbackSvg="album" showFallback={!visible[i]}
                                       borderRadius="0"/>
                </div>
            {/if}
            <div class="title-box overflow">
                <SkeletonText class="title overflow" length="5rem" skeleton={!visible[i]}>{track.name}</SkeletonText>
                <SkeletonText class="artists overflow" length="20rem"
                              skeleton={!visible[i]}>{track.artists.map(a => a.name).join(", ")}</SkeletonText>
            </div>
            <SkeletonText class="album overflow" skeleton={!visible[i]}>{track.album?.name}</SkeletonText>
            {#if showAddedAt}
                <SkeletonText class="date overflow" skeleton={!visible[i]}>{formateDate(track.added_at)}</SkeletonText>
            {/if}
            <SkeletonText class="time overflow" skeleton={!visible[i]}>{msToTime(track.duration_ms)}</SkeletonText>
        </div>
    {/each}
</div>


<style lang="scss">
  .table {

    .row {
      color: var(--text-subdued);
      width: 100%;
      display: flex;
      flex-direction: row;
      align-items: center;
      gap: 1rem;
      height: 3rem;
      border-radius: 0.4rem;
      padding: 0.5rem 0;

      .position {
        min-width: max(2rem, 2%);
        max-width: max(2rem, 2%);
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
      }

      .image-box {
        min-width: 3rem;
        max-width: 3rem;
      }

      .title-box {
        display: flex;
        flex-direction: column;
        margin-right: auto;
        min-width: 10rem;

        // :global is needed since the class is passed to a component
        :global(.title) {
          font-weight: 500;
          color: var(--text-base);
        }
      }

      // :global is needed since the class is passed to a component
      :global(.album) {
        margin-right: 15%;
        min-width: 15%;
        max-width: 15%;
      }

      // :global is needed since the class is passed to a component
      :global(.date) {
        min-width: 7rem;
        max-width: 7rem;
      }

      // :global is needed since the class is passed to a component
      :global(.time) {
        min-width: max(5rem, 4%);
        max-width: max(5rem, 4%);
      }

      // :global is needed since the class is passed to a component
      :global(.overflow) {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
    }

    .row:hover {
      background-color: var(--background-elevated-highlight);
      cursor: pointer;
    }

    .title-row {
      padding-bottom: 0;
    }

    .title-row:hover {
      background-color: inherit;
      cursor: unset;
    }
  }
</style>