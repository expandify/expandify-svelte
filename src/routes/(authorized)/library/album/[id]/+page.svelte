<script lang="ts">
    import {page} from "$app/stores";
    import Loading from "$lib/components/common/Loading.svelte";
    import {formateDate, msToTime} from "$lib/utils/converter/date-time";
    import TrackTable from "$lib/components/layout/TrackTable.svelte";
    import {toAlbum} from "$lib/utils/converter/spotify";
    import {spotifyApi} from "$lib/services/spotify/spotify-api";

    async function loadAndGet(id: string) {
        const {album, tracks} = await spotifyApi.loadAlbumWithTracks(id);
        return toAlbum(album, tracks)
    }


</script>

{#await loadAndGet($page.params.id)}
    <Loading title={"Album"}/>
{:then album}
    <div class="h-60 flex flex-row gap-8 mb-8">
        <img src={album.images?.at(0)?.url}
             class="rounded-2xl"
             alt={album.name}
             loading="lazy"/>
        <div class="flex flex-col justify-end">
            <span>Album</span>
            <h1 class="text-6xl mt-0 mb-4">{album.name}</h1>

            <div class="flex flex-row gap-1 whitespace-nowrap">
                <span class="font-bold">{album.artists.map(a => a.name).join(", ")}</span>
                <span>·</span>
                <span>{formateDate(album.release_date)}</span>
                <span>·</span>
                <span>{album.tracks.length} songs</span>
                <span>·</span>
                <span class="text-muted-foreground">{msToTime(album.tracks.reduce((d, a) => d + a.duration_ms, 0))}</span>
            </div>
        </div>
    </div>
    <TrackTable tracks={album.tracks} showImage={false} showAddedAt={false}/>
{/await}