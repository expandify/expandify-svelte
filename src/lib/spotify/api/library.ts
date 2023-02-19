import { loadSavedAlbumsWithTracks } from "./albums";
import { loadFollowedArtists } from "./artists";
import { loadUserPlaylistsWithTracks } from "./playlsits";
import { loadSavedTracks } from "./tracks";


export async function reloadLibrary() {
  loadUserPlaylistsWithTracks();
  loadSavedAlbumsWithTracks();
  loadSavedTracks();
  loadFollowedArtists();  
}