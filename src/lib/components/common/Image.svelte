<script lang="ts">
	import Icon from "./Icon.svelte";

  export let image: string | undefined | null;
  export let imageAlt: string | undefined | null;
  export let fallbackSvg: string;
  export let rounded: "full" | "little" | "none" = "little";

</script>


<div class="picture" >
  {#if image}
  <img src={image} 
       class="image" 
       alt={imageAlt} 
       class:rounded-full={rounded === "full"}
       class:rounded-little={rounded === "little"}
       loading="lazy"/>  
  {:else}
  <div class="fallback" class:rounded-full={rounded === "full"} class:rounded-little={rounded === "little"}>
    <Icon name={fallbackSvg} width={"auto"} height={"auto"} class="svg" ></Icon>
  </div>  
  {/if}
</div>


<style lang="scss">

  .rounded-full {
    border-radius: 100%;
  }

  .rounded-little {
    border-radius: 0.2rem;
  }

  .picture {    
    width: 100%;
    aspect-ratio: 1/1;      
    
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
    }
  }  

</style>