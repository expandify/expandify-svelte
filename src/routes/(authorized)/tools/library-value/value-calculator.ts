import { makeRequest } from "$lib/spotify/request";
import { albumStore } from "$lib/stores/library/albums"
import { playlistStore } from "$lib/stores/library/playlists";
import { trackStore } from "$lib/stores/library/tracks";
import { userStore } from "$lib/stores/library/user";
import { get } from "svelte/store"

const ESTIMATED_ALBUM_COST = 12.99
const ESTIMATED_SONG_COST = (0.99 + 1.29) / 2;
const ITUNES_ALBUM_LOOKUP = (upc: string) =>  `https://itunes.apple.com/lookup?upc=${upc}&entity=song`
type GroupedTracks = {[id: string]: Track[]};
type ITunesAlbum = {price: number, tracks: any[]};

export async function calculateLibraryValue(
  savedAlbums: boolean, 
  ownPlaylists: boolean, 
  followedPlaylists: boolean, 
  savedTracks: boolean) {
  
    
  
  let userId = get(userStore).user?.id;
  
  
  
  let savedAlbumTracks: Track[] = [];
  if (savedAlbums) {
    savedAlbumTracks = get(albumStore).albums.flatMap(a => a.tracks);
  }

  let ownPlaylistTracks: PlaylistTrack[] = [];
  if (ownPlaylists) {
    ownPlaylistTracks = get(playlistStore).playlists.filter(p => p.owner.id === userId).flatMap(p => p.tracks);
  }

  let followedPlaylistTracks: PlaylistTrack[] = [];
  if (followedPlaylists) {
    ownPlaylistTracks = get(playlistStore).playlists.filter(p => p.owner.id !== userId).flatMap(p => p.tracks);
  }

  let savedTracksList: SavedTrack[] = [];
  if (savedTracks) {
    savedTracksList = get(trackStore).tracks;
  }


  let tracks = [
    ...savedAlbumTracks,
    ...ownPlaylistTracks,
    ...followedPlaylistTracks,
    ...savedTracksList
  ]

  console.log("Loaded tracks");
  
  const groupedTracks = groupTracksByAlbum(tracks);

  console.log("Grouped Tracks by album ", Object.entries(groupedTracks).length);
  
  const price = await calculatePrice(groupedTracks);
  
  console.log("The calculated price is: ", price);
  return price;
  
}


function groupTracksByAlbum(tracks: Track[]): GroupedTracks {

  const combiner = (group: GroupedTracks, track: Track) => {

    const ts = (group[track.album.id] = group[track.album.id] || [])

    if(!ts.find((x) => x.id === track.id)) {
      ts.push(track);
    } 

    return group;
  };

  return tracks.reduce(combiner, {});
}


async function calculatePrice(groups: GroupedTracks) {
  const albumError: GroupedTracks = {}
  let calculatedPrice = 0;
  
  for (const [albumId, tracks] of Object.entries(groups)) {

    console.log("Calculating price for album: ", tracks.at(0)?.album.name);    
  
    try {
      let albumUpc = await getAlbumUpc(albumId);
      let itunesAlbum = await getItunesAlbum(albumUpc);
      const tracksPrice = await getItunesTrackPrices(itunesAlbum, tracks);
      calculatedPrice += Math.min(tracksPrice, itunesAlbum.price);
    } catch (err) {
      albumError[albumId] = tracks;
      continue;
    }            
  }

  console.log("Estimating the cost of all tracks not found in iTunes: ", Object.entries(albumError).length);
  
  return calculatedPrice + estimatePrice(albumError);
}

function estimatePrice(groups: GroupedTracks) {
  let estimatedPrice = 0;
  for (const [_, tracks] of Object.entries(groups)) { 
    const tracksCost = ESTIMATED_SONG_COST * tracks.length;
    estimatedPrice += Math.min(ESTIMATED_ALBUM_COST, tracksCost);
  }
  return estimatedPrice;
}



async function getItunesTrackPrices(album: ITunesAlbum, tracks: Track[]) {
  console.log("Calculating price for each track");
  let tracksPrices = 0; 
  const unknownSongs = [];

  for (const track of tracks) {
    const found = album.tracks.find((x: any) => x.discNumber === track.disc_number && x.trackNumber === track.track_number);
    if(!found) {
      // itunes and spotify have different songs on the same album
      unknownSongs.push(track);
      console.error("iTunes and Spotify contain different songs on the same album.");        
      continue;
    }
    tracksPrices += found.trackPrice;
  } 

  if (unknownSongs.length === tracks.length) {
    return Promise.reject();
  }

  tracksPrices += unknownSongs.length * ESTIMATED_SONG_COST;
  return tracksPrices;
}


async function getAlbumUpc(albumId: string) {
  let upc: string | null = null;
  try {
    upc = (await makeRequest((api) => api.getAlbum(albumId))).external_ids.upc || null;     
  } catch (err) {    
    console.error("Error getting album from Spotify");    
    return Promise.reject();
  }

  if(!upc) {
    console.error("No upc found");    
    return Promise.reject();
  }
  console.log(`Upc found: ${upc}`);
  
  return upc;
}

async function getItunesAlbum(upc: string): Promise<ITunesAlbum> {
  let itunes: any | null = null;

  try {
    itunes = await fetch(ITUNES_ALBUM_LOOKUP(upc));
    const json = await itunes.json();
    console.log("Found album in iTunes");
    return {
      price: json.results.at(0).collectionPrice || ESTIMATED_ALBUM_COST,
      tracks: json.results
    }

  } catch (error) {
    console.error("Error getting album from Itunes");
    return Promise.reject();
  }

  
}