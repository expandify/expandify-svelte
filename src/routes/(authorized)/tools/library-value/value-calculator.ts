import type { Album } from "$lib/classes/Album"
import type { Track } from "$lib/classes/Track";
import { getAlbumNoTracks } from "$lib/spotify/album";
import { albumCache, playlistCache, trackCache } from "$lib/stores/cache"
import { error } from "@sveltejs/kit";
import { InternalServer } from "@sveltejs/kit/types/internal";
import { get } from "svelte/store"

const ESTIMATED_ALBUM_COST = 12.99
const ESTIMATED_SONG_COST = (0.99 + 1.29) / 2;
const ITUNES_ALBUM_LOOKUP = (upc: string) =>  `https://itunes.apple.com/lookup?upc=${upc}&entity=song`

export async function calculateLibraryValue() {
  
  console.log("Calculating library cost.")

  const groupedTracks = groupTracksByAlbum(getAllTracks());

  const price = await calculatePrice(groupedTracks);
  

  console.log(`Your Library will cost ${price} USD.`);
}


function getAllTracks(): Track[] {
  return [
    ...get(trackCache).items, 
    ...get(albumCache).items.flatMap(a => a.tracks), 
    ...get(playlistCache).items.flatMap(p => p.tracks)
  ]
}

type GroupedTracks = {[id: string]: Track[]};
function groupTracksByAlbum(tracks: Track[]): GroupedTracks {

  const combiner = (group: GroupedTracks, track: Track) => {
    if (!track.album) {
      throw error(500, "The album of some tracks is missing.");
    }

    const ts = (group[track.album.id] = group[track.album.id] || [])

    if(!ts.find((x) => x.id === track.id)) {
      ts.push(track);
    } 

    return group;
  };

  return tracks.reduce(combiner, {});
}


async function calculatePrice(groups: GroupedTracks) {
  const noUpc: GroupedTracks = {}
  let price = 0;
  
  const entries = Object.entries(groups);
  for (const [index, [albumId, tracks]] of Object.entries(entries)) {

    let album: Album | null = null;
    try {
      album =  await getAlbumNoTracks(albumId);  
    } catch (err) {
      throw error(500, "Erro getting album from Spotify.");
    }
    

    if(!album.external_ids?.upc) {
      noUpc[albumId] = tracks;
      continue;
    }

    let itunes: any | null = null;
    let albumJson: any | null = null;
    let albumPrice = 0;

    try {
      itunes = await fetch(ITUNES_ALBUM_LOOKUP(album.external_ids.upc));  
      albumJson = await itunes.json();
      albumPrice = albumJson.results.at(0).collectionPrice;  
      if (!albumPrice) {
        throw error(500, "No album price.")
      }
    } catch (error) {
      noUpc[albumId] = tracks;
      continue;
    }
    
    
    let tracksPrices = 0;
    for (const track of tracks) {
      const found = albumJson.results.find((x: any) => x.discNumber === track.disc_number && x.trackNumber === track.track_number);
      if(!found) {
        // itunes and spotify have different songs on the same album
        noUpc[albumId] = noUpc[albumId] || [];
        noUpc[albumId].push(track);
        continue;
      }
      tracksPrices += found.trackPrice;
    }    

    price += Math.min(tracksPrices, albumPrice);
    console.log(`Album ${index} of ${entries.length}`);
  }

  console.log(`----------------`);
  console.log(`The combined price from itunes is: ${price}`);
  console.log(`----------------`);

  console.log(`Estimating a price for all tracks that were not found.`);

  let estimated = 0;
  for (const [_, tracks] of Object.entries(groups)) { 
    const tracksCost = ESTIMATED_SONG_COST * tracks.length;
    estimated += Math.min(ESTIMATED_ALBUM_COST, tracksCost);
  }
  console.log(`Estimating price for missing songs is: ${estimated} USD.`);

  return price + estimated;
}
