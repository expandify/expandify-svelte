<script context="module">
  export function load({error, status}) {
    return {
      props: {
        error: error,
        status: status
      }
    };
  }
</script>

<script>
  import {HomeIcon} from 'svelte-feather-icons'
  import {goto} from "$app/navigation";

  export let error;
  export let status;
</script>

<div class="error">
  <h1 class="status">{status}</h1>
  <h2 class="message">{error?.message}</h2>
  {#if status !== 404}
    <p class="help">Try to log out and back in again.</p>
  {/if}
  <div class="home" on:click={() => goto("/")}>
    <HomeIcon size="100%"/>
    <p>Back to Homepage</p>
  </div>
</div>

<style lang="scss">
  .error {
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: 100%;
    align-items: center;

    .status {
      color: var(--accent);
      font-size: 5rem;
      margin-bottom: 0;
    }

    .message {
      font-size: 3rem;
      margin-bottom: 12rem;
    }

    .help {
      font-size: 1.5rem;
      margin-bottom: 3rem;
    }

    .home {
      display: flex;
      flex-direction: row;
      font-size: 3rem;
      height: 3rem;
      white-space: nowrap;
      gap: 1rem;

      p {
        margin: 0;
      }
    }

    .home:hover {
      color: var(--accent);
      cursor: pointer;
    }
  }
</style>