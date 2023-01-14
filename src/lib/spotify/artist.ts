import { handleRequest } from "./request"
import { Cache } from "$lib/stores/cache"
import { Artist } from "$lib/classes/Artist"
import type SpotifyWebApi from "spotify-web-api-js"
import { makeCursorRequest } from "./request"

export async function reloadFollowedArtists() {

  Cache.clear(Cache.artists);
  Cache.setState(Cache.artists, Cache.State.Loading);  
  let currentLoaded = 0;

  const func = async (api: SpotifyWebApi.SpotifyWebApiJs, after: string | null) => (await api.getFollowedArtists({limit: 10, ...(after && {after: after})})).artists
  const artists = (await makeCursorRequest(func, (cursor) => {
    currentLoaded = currentLoaded + cursor.items.length;
    Cache.setTotalItems(Cache.artists, cursor.total!);
    Cache.setLoadedItems(Cache.artists, currentLoaded);
  })).map(a => Artist.fromFullArtist(a));
  
  Cache.setItems(Cache.artists, artists);
  Cache.setState(Cache.artists, Cache.State.Finished);  
}
