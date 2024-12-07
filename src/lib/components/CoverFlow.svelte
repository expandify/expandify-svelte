<script lang="ts">
	// Taken from:
	//   - https://scroll-driven-animations.style/demos/cover-flow/css/
	//   - https://www.youtube.com/watch?v=Dk1YA8dCgE0&list=PLNYkxOF6rcICM3ttukz9x5LCNOHfWBVnn&index=7

	import { albums } from '$lib/stores/library/albums';

	let slider: HTMLElement;
	let dragging = false;
	let startX: number;
	let scrollLeft: number;

	function pointerdown(e: PointerEvent) {
		dragging = true;
		startX = e.pageX - slider.offsetLeft;
		scrollLeft = slider.scrollLeft;
	}

	function pointermove(e: PointerEvent) {
		if (!dragging) {
			return;
		}
		e.preventDefault();
		const x = e.pageX - slider.offsetLeft;
		const walk = (x - startX) * 3; //scroll-fast
		slider.scrollLeft = scrollLeft - walk;
	}

</script>

<ul bind:this={slider}
		onpointerdown={pointerdown}
		onpointerleave={() => dragging = false}
		onpointerup={() => dragging = false}
		onpointermove={pointermove}
		class:snap-x={!dragging}
		class="cards">
	{#each $albums.albums as a}
		<li>
			<img src={a.images?.at(0)?.url} alt={a.name} draggable="false" />
		</li>
	{/each}
</ul>


<style>
    :root {
        --cover-size: 15rem;
    }

    @media (max-width: 1750px) {
        :root {
            --cover-size: 11rem;
        }
    }

    @media (max-width: 1360px) {
        :root {
            --cover-size: 8rem;
        }
    }

    @media (max-width: 1075px) {
        :root {
            --cover-size: 6rem;
        }
    }

    @keyframes adjust-z-index {
        0% {
            z-index: 1;
        }
        50% {
            z-index: 100; /* When at the center, be on top */
        }
        100% {
            z-index: 1;
        }
    }

    @keyframes rotate-cover {
        0% {
            transform: translateX(-100%) rotateY(-45deg);
        }
        35% {
            transform: translateX(0) rotateY(-45deg);
        }
        50% {
            transform: rotateY(0deg) translateZ(1em) scale(1.5);
        }
        65% {
            transform: translateX(0) rotateY(45deg);
        }
        100% {
            transform: translateX(100%) rotateY(45deg);
        }
    }

    .cards {

        min-height: calc(var(--cover-size) * 2.5);
        width: calc(var(--cover-size) * 6);
        margin: 0 auto;
        padding: calc(var(--cover-size) / 3 * 2) 0;
        position: relative;
        max-width: 90vw;
        list-style: none;
        overflow-x: scroll;
        white-space: nowrap;
        box-sizing: border-box;

    }

    .cards li {

        /* Track this element as it intersects the scrollport */
        view-timeline-name: --li-in-and-out-of-view;
        view-timeline-axis: inline;
        /* Link an animation to the established view-timeline and have it run during the contain phase */
        animation: linear adjust-z-index both;
        animation-timeline: --li-in-and-out-of-view;
        perspective: 40em;
        position: relative;
        z-index: 1;
        will-change: z-index;
        user-select: none;

        display: inline-block;
        width: var(--cover-size);
        aspect-ratio: 1;
        scroll-snap-align: center;

    }

    .cards li:first-of-type {

        margin-left: calc(50% - (var(--cover-size) / 2));
    }

    .cards li:last-of-type {

        margin-right: calc(50% - (var(--cover-size) / 2));
    }

    .cards li img {
        width: 100%;
        height: auto;
        -webkit-box-reflect: below 0.5em linear-gradient(rgb(0 0 0 / 0), rgb(0 0 0 / 0.25));
        /* Link an animation to the established view-timeline (of the parent li) and have it run during the contain phase */
        animation: linear rotate-cover both;
        animation-timeline: --li-in-and-out-of-view;
    }

</style>