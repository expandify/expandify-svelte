<script lang="ts">
	export let title: string;
</script>

  <section id=section>
      <h5 id=title>{title}</h5>
      <hr id=divider />
      <div id=slot>
        <slot></slot>
      </div>      
  </section>


<style lang="scss">
	
	#section {
    display: flex;
    flex-direction: column;

    #title {
      font-size: var(--font-size);
      margin-bottom: 0.5rem;
      margin-top: 3rem;
    }

    #divider {
			border: none;
			border-top: 0.2rem solid var(--background-highlight);
      width: 100%;
    }

    #slot {
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }
  }
</style>
