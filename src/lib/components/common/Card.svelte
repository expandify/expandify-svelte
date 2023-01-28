<script lang="ts">
	import { fade } from "svelte/transition";
	import Image from "./Image.svelte";

  
  export let title: string;
  export let subtitle: string | null | undefined;
  export let href: string | null | undefined = "";

  export let image: string | undefined | null;
  export let fallbackSvg: string;

  export let imgRounded: "full" | "little" | "none" = "little";
  
</script>

<a in:fade href={href}>
  <div class="card" >
    <Image image={image} 
          fallbackSvg={fallbackSvg} 
          rounded={imgRounded}
          imageAlt={title}/>  
    
    
    <div class="card-bottom">
      <div class="title">{title}</div>
      <div class="subtitle">{subtitle || ""}</div>
    </div>
  </div>
</a>
<style lang="scss">


  .card {    
    padding: 1rem;
    border-radius: 0.3rem;
    background-color: var(--background-elevated-base); 


      .card-bottom {
        
        margin-top: 0.7rem;
        .title {
          font-size: 1.2rem;
          margin-bottom: 0.2rem;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
          color: var(--text-base);
        }

        .subtitle {
          font-size: 0.8rem;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
          color: var(--text-subdued);
        }
    }    
  }

  .card:hover {
    cursor: pointer;
    background-color: var(--background-elevated-highlight);
    
  }
</style>