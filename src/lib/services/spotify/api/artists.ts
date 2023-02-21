import { makeRequest } from "../request";

type LoadingCallback = (artist: SpotifyApi.ArtistObjectFull, total: number) => Promise<void>;
export async function loadFollowedArtists(callback: LoadingCallback) {
  let after: string | null = null;
  let next: string;

  do {
    const data = await makeRequest((api) => api.getFollowedArtists({ limit: 50, ...(after && { after: after }) }));
    after = data.artists.cursors.after;
    next = data.artists.next; 

    for (const artist of data.artists.items) {
      await callback(artist, data.artists.total || 0);
    }    
  } while(next)
}