import type { LibraryComponentDependencyStore } from "$lib/types/dependencies";
import { writable } from "svelte/store";

function createStore() {
  const data: LibraryComponentDependencyStore = { albums: false, artists: false, playlists: false, tracks: false, user: false };
  const { subscribe, set, update } = writable(data);

	return {
		subscribe, 
    noNeeds: () => {set(data)},
    albumsNeeded: () => { update(d => ({...d, albums: true})) },
    onlyAlbumsNeeded: () => { update(_ => ({...data, albums: true})) },

    artistsNeeded: () => { update(d => ({...d, artists: true})) },
    onlyArtistsNeeded: () => { update(_ => ({...data, artists: true})) },

    playlistsNeeded: () => { update(d => ({...d, playlists: true})) },
    onlyPlaylistsNeeded: () => { update(_ => ({...data, playlists: true})) },

    tracksNeeded: () => { update(d => ({...d, tracks: true})) },
    onlyTracksNeeded: () => { update(_ => ({...data, tracks: true})) },

    userNeeded: () => { update(d => ({...d, user: true})) },
    onlyUserNeeded: () => { update(_ => ({...data, user: true})) },

    setDependencies: (albums: boolean, artists: boolean, playlists: boolean, tracks: boolean, user: boolean) => {set({ albums, artists, playlists, tracks, user })}    
	};
}

export const dependencies = createStore();