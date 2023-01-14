<script lang="ts">

  export let name: string;
  export let value: number | null = null;
  export let max: number | null = null;

  
</script>

  <article class="floating">

    <h6 class="header">Loading {name}</h6>      
    
    {#if value && max}
    <progress value={value} max={max}></progress>  
    {:else}
    <progress></progress>  
    {/if}
  </article>




<style lang="scss">

.floating {
  margin: 1rem;
  padding: 0.5rem 1rem;
  width: 11rem; 

  .header {    
    display: flex;
    align-items: center;
    justify-content: center;      
    margin-bottom: 0.5rem;
  }
  progress { 
    margin: 0;
  }    
}
</style>