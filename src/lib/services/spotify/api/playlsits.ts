import { makeRequest } from "../request";

type LoadingCallback = (playlist: SpotifyApi.PlaylistObjectFull, tracks: SpotifyApi.PlaylistTrackObject[], total: number) => Promise<void>;
export async function loadUserPlaylistsWithTracks(callback: LoadingCallback) {
  let offset = 0;
  let next: string;
  
  do {
    // Typescript throws a warning: {} not assignable to string
    // This can be ignored, since the library does some fuckery
    // @ts-ignore
    const data = await makeRequest((api) => api.getUserPlaylists({ limit: 50, offset: offset }));
    offset += data.limit;
    next = data.next;
    
    for (const playlist of data.items) {
      const playlistWithTracks = await loadPlaylistWithTracks(playlist);
      await callback(playlistWithTracks.playlist, playlistWithTracks.tracks, data.total);
    } 

  } while(next)
}

async function loadPlaylistWithTracks(playlistSimplified: SpotifyApi.PlaylistObjectSimplified) {
  const playlistFull = await makeRequest((api) => api.getPlaylist(playlistSimplified.id));

  let next = playlistFull.tracks.next;
  let offset = 0;
  let tracks: SpotifyApi.PlaylistTrackObject[] = [];

  while(next) {
    const data = await makeRequest((api) => api.getPlaylistTracks(playlistSimplified.id, { limit: 50, offset }));
    offset += data.limit;
    next = data.next;
    tracks = [...tracks, ...data.items]
  }

  return { playlist: playlistFull, tracks: tracks };
}


