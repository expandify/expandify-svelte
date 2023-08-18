<script lang="ts">
    export let title: string;
    export let metric: number;

</script>


<div class="card">
    <h1 class="metric">{metric}</h1>

    <div class="title">{title}</div>
</div>

<style lang="scss">


  .card {
    padding: 2rem;
    border-radius: 2rem;
    background-color: var(--background-elevated-base);
    width: 8rem;
    height: 10rem;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;

    .metric {
      font-size: 3rem;
      margin: 0;
    }

    .title {
      font-size: 1.2rem;
    }
  }
</style>