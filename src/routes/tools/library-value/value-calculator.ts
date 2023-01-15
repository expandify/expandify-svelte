import type { Album } from "$lib/classes/Album"
import type { Track } from "$lib/classes/Track";
import { getAlbumNoTracks } from "$lib/spotify/album";
import { albumCache, playlistCache, trackCache } from "$lib/stores/cache"
import { get } from "svelte/store"

const ITUNES_ALBUM_LOOKUP = (upc: string) =>  `https://itunes.apple.com/lookup?upc=${upc}&entity=song`

export async function calculateLibraryValue() {
  
  console.log("Calculating Albums.")
  const albumsPrice = await calculateAlbumsPrice();
  console.log(`Albums will cost ${albumsPrice} USD.`);
  

  console.log("Calculating Tracks.")
  const tracksPrice = await calculateTracksPrice(get(trackCache).items);
  console.log(`Tracks will cost ${tracksPrice} USD.`);

  console.log("Calculating Playlists.")
  const playlistsPrice = await calculatePlaylistsPrice();
  console.log(`Playlists will cost ${playlistsPrice} USD.`);

  console.log(`Your Library will cost ${tracksPrice + albumsPrice + playlistsPrice} USD.`);
}


async function calculateAlbumsPrice() {
  const noUpc: Album[] = []
  let price = 0;

  for (const album of get(albumCache).items) {
    const upc = album.external_ids?.upc;
    if (!upc) {
      noUpc.push(album);
      continue;
    }
    const itunes = await fetch(ITUNES_ALBUM_LOOKUP(upc));
    const albumJson = await itunes.json();
    try {
      const albumPrice = albumJson.results.at(0).collectionPrice;  
      if (!albumPrice) {
        noUpc.push(album);
        continue;
      }
      price += albumPrice;
    } catch (error) {
      noUpc.push(album);
      continue;
    }
    
  }

  console.log("Errors Albums: ", noUpc);
  return price;
}

async function calculateTracksPrice(tracks: Track[]) {
  const noAlbumNoUpcNoItunes: Track[] = []
  const tracksWithAlbum: Track[] = []
  let price = 0;

  for (const track of tracks) {    
    const trackAlbum = track.album;    

    if(!trackAlbum) {
      noAlbumNoUpcNoItunes.push(track);
      continue;
    } 
    tracksWithAlbum.push(track);
  }


  const upcGroups = tracksWithAlbum.reduce((group: {[id: string]: Track[]}, track: Track) => {
    const ts = (group[track.album!.id] = group[track.album!.id] || [])

    if(!ts.find((x) => x.track_number === track.track_number && x.disc_number === track.disc_number)) {
      ts.push(track);
    } 

    return group;
  }, {});
  
  const albumCount = Object.entries(upcGroups).length;
  for (const [index, [albumId, tracks]] of Object.entries(Object.entries(upcGroups))) {

    let album: Album | null = null;
    try {
      album =  await getAlbumNoTracks(albumId);  
    } catch (error) {
      console.log("Error getting album: ", albumId);
      continue;
    }
    

    if(!album.external_ids?.upc) {
      tracks.forEach((x) => noAlbumNoUpcNoItunes.push(x));
      continue;
    }

    let itunes: any | null = null;
    let albumJson: any | null = null;

    try {
      itunes = await fetch(ITUNES_ALBUM_LOOKUP(album.external_ids.upc));  
      albumJson = await itunes.json();
    } catch (error) {
      tracks.forEach((x) => noAlbumNoUpcNoItunes.push(x));
      continue;
    }

    let albumPrice = 0;
    try {
      albumPrice = albumJson.results.at(0).collectionPrice;  
      if (!albumPrice) {
        tracks.forEach(t => noAlbumNoUpcNoItunes.push(t));
        continue;
      }
    } catch (error) {
      tracks.forEach(t => noAlbumNoUpcNoItunes.push(t));
      continue;      
    }
    
    
    let tracksPrices = 0;
    for (const track of tracks) {
      const found = albumJson.results.find((x: any) => x.discNumber === track.disc_number && x.trackNumber === track.track_number)
      if(!found) {
        noAlbumNoUpcNoItunes.push(track);
        continue;
      }
      tracksPrices += found.trackPrice;
    }    

    price += Math.min(tracksPrices, albumPrice);
    console.log(`Album ${index} of ${albumCount}. Current Price: ${price}`);
  }

  console.log("Errors Tracks: ", noAlbumNoUpcNoItunes);

  return price;
}

async function calculatePlaylistsPrice() {
  const tracks = get(playlistCache).items.flatMap((playlist) => playlist.tracks).filter((t) => t.type === "track");
  
  return calculateTracksPrice(tracks);

}