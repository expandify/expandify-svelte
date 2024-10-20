<script lang="ts">
	import { albums } from '$lib/stores/library/albums';
	import ImageWithFallback from '$lib/components/common/ImageWithFallback.svelte';
	import { onMount } from 'svelte';

	// Code taken from "https://github.com/shuding/coverflow" and modified.

	export let width = 600;
	export let spacing = 20;
	export let size = 180;
	export let flat = false;

	let browserPrefix = "";
	if (navigator.userAgent.indexOf('Firefox') != -1) {
		browserPrefix = "-moz-";
	} else if (navigator.userAgent.indexOf('Chrome') != -1) {
		browserPrefix = "-webkit-";
	} else if (navigator.userAgent.indexOf('Safari') != -1) {
		browserPrefix = "-webkit-";
	}

	function setTransform3D(el: HTMLElement, degree: number, perspective: number, z: number) {
		degree = Math.max(Math.min(degree, 90), -90);
		z -= 5;
		el.style['perspective'] = perspective + 'px';
		el.style['transform'] = 'rotateY(' + degree + 'deg) translateZ(' + z + 'px)';
	}

	function displayIndex(left: number, imgs: HTMLElement[], index: number) {
		let mLeft = (width - size) * .5 - spacing * (index + 1) - size * .5;
		for (let i = 0; i <= index; ++i) {
			imgs[i].style.left = (left + i * spacing + spacing) + 'px';
			imgs[i].style.marginLeft = mLeft + 'px';
			imgs[i].style.zIndex = (i + 1).toString();
			setTransform3D(imgs[i], flat ? 0 : ((index - i) * 10 + 45), 300, flat ? -(index - i) * 10 : (-(index - i) * 30 - 20));
		}
		imgs[index].style.marginLeft = (mLeft + size * .5) + 'px';
		imgs[index].style.zIndex = imgs.length.toString();
		setTransform3D(imgs[index], 0, 0, 5);
		for (let i = index + 1; i < imgs.length; ++i) {
			imgs[i].style.left = (left + i * spacing + spacing) + 'px';
			imgs[i].style.marginLeft = (mLeft + size) + 'px';
			imgs[i].style.zIndex = (imgs.length - i).toString();
			setTransform3D(imgs[i], flat ? 0 : ((index - i) * 10 - 45), 300, flat ? (index - i) * 10 : ((index - i) * 30 - 20));
		}
	}

	function coverflowScroll(c: HTMLElement, imgs: HTMLElement[]) {
		let p = c.scrollLeft / width;
		let index = Math.min(Math.floor(p * imgs.length), imgs.length - 1);
		let left = c.scrollLeft;
		c.dataset.index = index.toString();
		displayIndex(left, imgs, index);
	}

	function initCoverFlow(c: HTMLElement) {
		let index = c.dataset.index,
			imgHeight = 0,
			imgs: HTMLElement[] = [],
			placeholding;
		for (let childNode of c.childNodes) {
			if (childNode instanceof HTMLElement && (<HTMLElement>childNode).tagName) {
				imgs.push(<HTMLElement>childNode);
			}
		}
		for (let i = 0; i < imgs.length; ++i) {
			imgs[i].style.position = 'absolute';
			imgs[i].style.width = size + 'px';
			imgs[i].style.height = 'auto';
			imgs[i].style.bottom = '60px';
			imgs[i].style['transition'] = browserPrefix + 'transform .4s ease, margin-left .4s ease, -webkit-filter .4s ease';
			imgHeight = Math.max(imgHeight, imgs[i].getBoundingClientRect().height);
		}
		setTransform3D(c, 0, 600, 0);
		placeholding = document.createElement('DIV');
		placeholding.style.width = (width ? width * 2 : (size + (imgs.length + 1) * spacing) * 2) + 'px';
		placeholding.style.height = '1px';
		c.appendChild(placeholding);
		if (width)
			c.style.width = width + 'px';
		else
			c.style.width = (width ? width : (size + (imgs.length + 1) * spacing)) + 'px';
		c.style.height = (imgHeight + 80) + 'px';
		c.style.position = 'relative';
		c.dataset.index = (index ? parseInt(index) : 0).toString();
		c.onscroll = function() {
			coverflowScroll(c, imgs);
		};
		for (let i = 0; i < imgs.length; ++i)
			imgs[i].onclick = function() {
				displayIndex(c.scrollLeft, imgs, imgs.indexOf(imgs[i]));
			};
		displayIndex(c.scrollLeft, imgs, +c.dataset.index);
	}

	let coverflow: HTMLElement;
	onMount(() => {
		initCoverFlow(coverflow);
	})

	function transformScroll(event: WheelEvent & {   currentTarget: (EventTarget & HTMLDivElement)}) {
		if (!event.deltaY) {
			return;
		}

		event.currentTarget.scrollLeft += event.deltaY + event.deltaX;
		event.preventDefault();
	}
</script>
<div bind:this={coverflow}
		 class="flex flex-col h-full no-scrollbar overflow-x-scroll"
		on:wheel="{transformScroll}">
	{#each $albums.albums as a}
		<ImageWithFallback type="{a}" borderRadius="0"></ImageWithFallback>

	{/each}
</div>
