<script lang="ts">
  import type { Album, Artist, Playlist } from "$lib/types/spotify";
	import { fade } from "svelte/transition";
  import ImageWithFallback from "../common/ImageWithFallback.svelte";

  export let card: Album | Artist | Playlist ;
  
  let title = card.name;
  let subtitle: string;
  let href: string | null;

  switch (card.type) {
    case "album":
      subtitle = card.artists.map(a => a.name).join(", ")
      href = `/information/album/${card.id}`
      break;
    case "artist":  
      subtitle = "Artist"
      href = `/information/artist/${card.id}`      
      break;
    case "playlist":
      subtitle = `By ${card.owner.display_name}`
      href = `/information/playlist/${card.id}`
      break;
  
  }
  
</script>

<a in:fade href={href}>
  <div class="card" >
    <ImageWithFallback type={card}/>    
    
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