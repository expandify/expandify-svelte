<script lang="ts">
	import Image from "./Image.svelte";

  
  export let title: string;
  export let subtitle: string | null | undefined;
  export let href: string | null | undefined = "";

  export let image: string | undefined | null;
  export let fallbackSvg: string;

  export let imgRounded: "full" | "little" | "none" = "little";
  
</script>

<a class="card" href={href}>

  <Image image={image} 
         fallbackSvg={fallbackSvg} 
         rounded={imgRounded}
         imageAlt={title}/>  
  
  
  <div class="card-bottom">
    <div class="title">{title}</div>
    <div class="subtitle">{subtitle || ""}</div>
  </div>
</a>

<style lang="scss">


  .card {    
    padding: 1rem;
    border-radius: 0.3rem;
    background-color: var(--background-elevated-base);
    transition: 0.3s;
    text-decoration: none;
    
    .rounded {
      border-radius: 100%;
    }

    .picture {
      border-radius: 0.2rem;

      .fallback {
        background-color: var(--background-elevated-highlight);
        box-shadow: 0 0 1rem 1rem var(--background-base);
        position: relative;
        width: 100%;      
        margin-bottom: 0.7rem;

        .svg-sizer {        
          position:absolute;
          width: 100%;  
          height: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          :global(.svg) {       
            width: 40%;
          }
        }
        
      }

      .fallback:after {
        content: "";
        display: block;
        padding-bottom: 100%;
      }

      .image {
        width: 100%;
        object-fit: cover;
        aspect-ratio: 1/1;        
        margin-bottom: 0.7rem;
      }
    }    

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
    color: unset;
    background-color: var(--background-elevated-highlight);
    
  }
</style>