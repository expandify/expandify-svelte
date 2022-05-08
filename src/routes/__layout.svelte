<script>
  import "../styles/app.scss";
  import Footer from "../lib/components/Footer.svelte";
</script>

<div class="is-flex is-flex-direction-column min-vh-100 is-justify-content-space-between">
  <slot />
  <Footer/>
</div>