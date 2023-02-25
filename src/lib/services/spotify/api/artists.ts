import { user } from "$lib/stores/library/user";
import { get } from "svelte/store";
import { makeRequest } from "../request";

type LoadingCallbackFollowed = (artist: SpotifyApi.ArtistObjectFull, total: number) => Promise<void>;
export async function loadFollowedArtists(callback: LoadingCallbackFollowed) {
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

export async function getArtist(id: string) {
  const artist = await makeRequest((api) => api.getArtist(id));
  
  return artist;
}

type LoadingCallbackAlbums = (album: SpotifyApi.AlbumObjectSimplified, total: number) => Promise<void>;
export async function getArtistsAlbums(id: string, callback: LoadingCallbackAlbums) {
  let offset = 0;
  let next: string;

  do {    
    const data = await makeRequest((api) => api.getArtistAlbums(id, { limit: 50, offset: offset }));
    offset += data.limit;
    next = data.next;
    
    for (const album of data.items) {       
      await callback(album, data.total); 
    }   
  } while(next)
}

export async function getArtistTopTracks(id: string) {
  const response = await makeRequest((api) => api.getArtistTopTracks(id, ""));
  
  return response.tracks;
}

export async function getArtistRelatedArtists(id: string) {
  const response = await makeRequest((api) => api.getArtistRelatedArtists(id));
  
  return response.artists;
}