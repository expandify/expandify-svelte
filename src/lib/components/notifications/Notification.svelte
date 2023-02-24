<script lang="ts">
	import { NotificationType, type Notification } from '$lib/types/notification';
	import { fade } from 'svelte/transition';

  export let notification: Notification;  


</script>

<div class="banner" 
     transition:fade
     class:announcement={notification.type === NotificationType.ANNOUNCEMENT}
     class:error={notification.type === NotificationType.ERROR}
     class:success={notification.type === NotificationType.SUCCESS}
     class:warning={notification.type === NotificationType.WARNING}>
  { notification.message }
</div>




<style lang="scss">
	.banner {
    font-size: var(--font-size-h3);
    padding: 0.5rem 1rem ;
    width: 60%;
    min-width: 10rem;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    border-radius: 10rem;
    box-shadow: 0 0 1rem 0 var(--background-press);
    pointer-events: auto;
  }

  .announcement {
    background-color: var(--essential-announcement);
  }

  .error {
    background-color: var(--essential-negative);
  }

  .success {
    background-color: var(--essential-positive);
  }

  .warning {
    background-color: var(--essential-warning);
  }
</style>
