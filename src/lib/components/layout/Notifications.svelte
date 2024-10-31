<script lang="ts">
    import {notifications} from '$lib/stores/notifications';
    import {NotificationType} from '$lib/types/notification';
    import {fade} from 'svelte/transition';

    let iterator = $derived(Array.from($notifications));
</script>

<div class="banners">

    {#each iterator as notification (notification.time)}
        <div class="banner"
             transition:fade
             class:announcement={notification.type === NotificationType.ANNOUNCEMENT}
             class:error={notification.type === NotificationType.ERROR}
             class:success={notification.type === NotificationType.SUCCESS}
             class:warning={notification.type === NotificationType.WARNING}>
            { notification.message }
        </div>
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

    .banner {
      font-size: var(--font-size-h3);
      padding: 0.5rem 1rem;
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
  }
</style>
