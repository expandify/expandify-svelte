<script lang="ts">
  export let name: string;
  export let width = "auto";
  export let height = "auto";
  export let focusable = "false";
  let icons = [
      {
        box: 24,
        name: "album",
        svg: `<path d="M12 3a9 9 0 100 18 9 9 0 000-18zM1 12C1 5.925 5.925 1 12 1s11 4.925 11 11-4.925 11-11 11S1 18.075 1 12z"></path><path d="M12 10a2 2 0 100 4 2 2 0 000-4zm-4 2a4 4 0 118 0 4 4 0 01-8 0z"></path>`
      },
      {
        box: 24,
        name: "artist",
        svg: `<path d="M13.363 10.474l-.521.625a2.499 2.499 0 00.67 3.766l.285.164a5.998 5.998 0 011.288-1.565l-.573-.33a.5.5 0 01-.134-.754l.52-.624a7.372 7.372 0 001.837-4.355 7.221 7.221 0 00-.29-2.489 5.644 5.644 0 00-3.116-3.424A5.771 5.771 0 006.753 2.87a5.7 5.7 0 00-1.19 2.047 7.22 7.22 0 00-.29 2.49 7.373 7.373 0 001.838 4.355l.518.622a.5.5 0 01-.134.753L3.5 15.444a5 5 0 00-2.5 4.33v2.231h13.54a5.981 5.981 0 01-1.19-2H3v-.23a3 3 0 011.5-2.6l3.995-2.308a2.5 2.5 0 00.67-3.766l-.521-.625a5.146 5.146 0 01-1.188-4.918 3.71 3.71 0 01.769-1.334 3.769 3.769 0 015.556 0c.346.386.608.84.768 1.334.157.562.22 1.146.187 1.728a5.379 5.379 0 01-1.373 3.188zm7.641-1.173a1 1 0 00-1 1v4.666h-1a3 3 0 103 3v-7.666a.999.999 0 00-1.003-1h.003zm-1 8.666a1 1 0 11-1-1h1v1z"></path>`
      },
      {
        box: 24,
        name: "playlist",
        svg: `<path d="M6 3h15v15.167a3.5 3.5 0 11-3.5-3.5H19V5H8v13.167a3.5 3.5 0 11-3.5-3.5H6V3zm0 13.667H4.5a1.5 1.5 0 101.5 1.5v-1.5zm13 0h-1.5a1.5 1.5 0 101.5 1.5v-1.5z"></path>`
      }, 
      {
        box: 24,
        name: "play",
        svg:`<path d="M3 22v-20l18 10-18 10z"/>`
      }, 
      {
        box: 24,
        name: "pause",
        svg:`<path d="M11 22h-4v-20h4v20zm6-20h-4v20h4v-20z"/>` 
      }, 
      {
        box: 24,
        name: "next",
        svg:`<path d="M19 12l-18 12v-24l18 12zm4-11h-4v22h4v-22z"/>`
      }, 
      {
        box: 24,
        name: "previous",
        svg:`<path d="M4 2v20h-2v-20h2zm18 0l-16 10 16 10v-20z"/>`
      }
  ];
  let displayIcon = icons.find((e) => e.name === name)!;
</script>


<svg class={$$props.class} 
     focusable={focusable}
     width={width} 
     height={height}
     viewBox="0 0 {displayIcon.box} {displayIcon.box}">
{@html displayIcon.svg}
</svg>
