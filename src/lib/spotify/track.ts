import { Track } from "$lib/classes/Track";
import { trackCache } from "$lib/stores/cache"
import type SpotifyWebApi from "spotify-web-api-js"
import { makePagingRequest } from "./request"

export async function realoadSavedTracks() {
  const func = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => api.getMySavedTracks({limit: 50, offset})
  const tracks = await makePagingRequest(func);

  trackCache.set(tracks.map(t => Track.fromSavedTrack(t)));
}