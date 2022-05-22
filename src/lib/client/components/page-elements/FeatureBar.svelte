<script>
  export let features;

  let currentFeature = null

  function featureClick(feature) {
    if (currentFeature === feature) {
      currentFeature = null;
    } else {
      currentFeature = feature
    }

  }
</script>


<div class="features">
  {#each features as feature}
    <div class="feature" class:clicked={currentFeature === feature} class:any-feature-open={currentFeature != null}
         on:click={() => featureClick(feature)}>
      {feature.title}
    </div>
  {/each}
</div>

{#if currentFeature != null}
  <div class="expanded-feature">
    {#if currentFeature.warning}
      <p class="warning">
        {currentFeature.warning}
      </p>
    {/if}
    <p>
      {currentFeature.content}
    </p>
  </div>
{/if}

<style lang="scss">

  .features {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;

    .feature {
      background-color: var(--bg-main-50);
      padding: 1rem;
      width: 100%;
      text-align: center;
    }

    .feature.clicked {
      background-color: var(--bg-main-100);
    }

    .feature:first-child {
      border-radius: 0.5rem 0 0 0.5rem;
    }

    .feature:last-child {
      border-radius: 0 0.5rem 0.5rem 0;
    }

    .feature:first-child.any-feature-open {
      border-radius: 0.5rem 0 0 0;
    }

    .feature:last-child.any-feature-open {
      border-radius: 0 0.5rem 0 0;
    }

    .feature:hover {
      background-color: var(--bg-main-100);
      cursor: pointer;
    }

  }

  .expanded-feature {
    background-color: var(--bg-main-100);
    padding: 2rem;
    border-radius: 0 0 0.5rem 0.5rem;

    .warning {
      color: var(--accent);
    }
  }


</style>