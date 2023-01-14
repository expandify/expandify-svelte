import { handleRequest } from "./request"
import { artistCache } from "$lib/stores/cache"
import { Artist } from "$lib/classes/Artist"
import type SpotifyWebApi from "spotify-web-api-js"
import { makeCursorRequest } from "./request"

export async function reloadFollowedArtists() {
  const func = async (api: SpotifyWebApi.SpotifyWebApiJs, after: string | null) => (await api.getFollowedArtists({limit: 10, ...(after && {after: after})})).artists
  const artists = await makeCursorRequest(func);

  artistCache.set(artists.map(a => Artist.fromFullArtist(a)));
}
