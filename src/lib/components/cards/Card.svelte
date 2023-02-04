<script lang="ts">
	import { fade } from "svelte/transition";

  
  export let title: string;
  export let subtitle: string | null;
  export let href: string | null = "";
  
</script>

<a in:fade href={href}>
  <div class="card" >
    <slot></slot>    
    
    <div class="card-bottom">
      <div class="title">{title}</div>
      <div class="subtitle">{subtitle || ""}</div>
    </div>
  </div>
</a>

<style lang="scss">


  .card {    
    padding: 1rem;
    border-radius: 1rem;
    background-color: var(--background-elevated-base);     
    max-width: 12rem;
    

      .card-bottom {
        
        margin-top: 1rem;
        .title {
          font-size: 1.2rem;
          margin-bottom: 0.5rem;
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