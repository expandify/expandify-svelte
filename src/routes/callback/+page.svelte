<script>
  import { page } from '$app/stores'; 
  
  const code = $page.url.searchParams.get("code")
  const state = $page.url.searchParams.get("state")
</script>

<p>Code: {code}</p>
<p>State: {state}</p>