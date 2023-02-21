import { makeRequest } from "../request";

type LoadingCallbackSavedTrack = (track: SpotifyApi.SavedTrackObject, total: number) => Promise<void>;
export async function loadSavedTracks(callback: LoadingCallbackSavedTrack) {
  let offset = 0;
  let next: string;

  do {
    const data = await makeRequest((api) => api.getMySavedTracks({ limit: 50, offset }));
    offset += data.limit;
    next = data.next;

    for (const track of data.items) {
      await callback(track, data.total);
    }    
  } while(next)
}

type LoadingCallbackFullTrack = (track: SpotifyApi.TrackObjectFull, total: number) => Promise<void>;
export async function loadSeveralTracks(trackIds: string[], callback: LoadingCallbackFullTrack) {
  const chunkSize = 50;

  for (let i = 0; i < trackIds.length; i += chunkSize) {
      const chunk = trackIds.slice(i, i + chunkSize);
      
      const data = await makeRequest((api) => api.getTracks(chunk));

      for (const track of data.tracks) {
        await callback(track, trackIds.length)
      }
  }
}


