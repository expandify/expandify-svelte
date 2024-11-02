<script lang="ts">
    import { Card, CardContent, CardDescription } from '$lib/components/ui/card';
    import { CardHeader, CardTitle } from '$lib/components/ui/card/index.js';

    let { title, metric } = $props<{
        title: string;
        metric: number;
    }>();

</script>

<Card>
    <CardHeader>
        <CardTitle>{metric}</CardTitle>

    </CardHeader>
    <CardContent>
        <CardDescription>{title}</CardDescription>
    </CardContent>
</Card>
