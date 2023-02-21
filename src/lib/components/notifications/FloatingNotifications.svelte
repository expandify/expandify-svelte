<script lang="ts">
	import { notifications } from '$lib/stores/notifications';
	import Notification from './Notification.svelte';
  

  $: iterator = Array.from($notifications);
</script>

<div class="banners">

  {#each iterator as notification (notification.time)}
    <Notification notification={notification} />
  {/each}

</div>


<style lang="scss">
	.banners {
    position: fixed;
    top: 2rem;
    display: flex;
    flex-direction: column;    
    justify-content: center;
    align-items: center;
    min-width: 100%;
    gap: 1rem;
    z-index: 5;
    pointer-events: none;
  }
</style>
