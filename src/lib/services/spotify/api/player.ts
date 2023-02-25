import { toPlaybackState } from "$lib/utils/converter/spotify";
import { makeRequest } from "../request";

export async function getPlaybackState() {
  return await makeRequest((api) => api.getMyCurrentPlaybackState());    
}

export async function startPlayback() {
  return await makeRequest((api) => api.play());  
}

export async function pausePlayback() {
  return await makeRequest((api) => api.pause());  
}

export async function nextPlayback() {
  return await makeRequest((api) => api.skipToNext());  
}

export async function previousPlayback() {
  return await makeRequest((api) => api.skipToPrevious());  
}