<script lang="ts">
	import Icon from "./FallbackSvg.svelte";

  export let image: string | undefined | null;
  export let imageAlt: string | undefined | null;
  export let fallbackSvg: string;
  export let borderRadius = "0";

</script>


<div class="picture" >
  {#if image}
  <img src={image} 
       class="image" 
       alt={imageAlt} 
       loading="lazy"
       style="border-radius: {borderRadius};"/>  
  {:else}
  <div class="fallback" style="border-radius: {borderRadius};">
    <Icon name={fallbackSvg} width={"auto"} height={"auto"} class="svg" ></Icon>
  </div>  
  {/if}
</div>


<style lang="scss">

  .picture {    
    
    aspect-ratio: 1/1;      
    height: 100%;

    .fallback {
      background-color: var(--background-elevated-highlight);
      box-shadow: 0 0 1rem 0.5rem var(--background-base);
      position: relative;
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;  
      height: 100%;
        
      :global(.svg) {       
        width: 40%;
        height: 40%;
      }
      
    }

    .image {
      max-width:100%;
      max-height:100%;
      object-fit: cover;
      aspect-ratio: 1/1;
    }
  }  

</style>