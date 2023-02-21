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
    padding: 1rem;
    width: 80%;
    min-width: 10rem;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    border-radius: 10rem;
    box-shadow: 0 0 0.2rem 0rem var(--background-elevated-highlight);    
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
