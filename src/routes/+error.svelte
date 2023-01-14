<script>
  import { page } from '$app/stores';
  console.error($page.status, $page.error)
</script>

<h1>{JSON.stringify($page.error, null, 2)}</h1>