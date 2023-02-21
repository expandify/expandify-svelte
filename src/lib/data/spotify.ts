import { loadAlbumWithTracks, loadSavedAlbumsWithTracks } from "$lib/services/spotify/api/albums";
import { loadFollowedArtists } from "$lib/services/spotify/api/artists";
import { loadUserPlaylistsWithTracks } from "$lib/services/spotify/api/playlsits";
import { loadSavedTracks } from "$lib/services/spotify/api/tracks";
import { laodUser } from "$lib/services/spotify/api/user";
import { albums } from "$lib/stores/library/albums";
import { artists } from "$lib/stores/library/artists";
import { playlists } from "$lib/stores/library/playlists";
import { tracks } from "$lib/stores/library/tracks";
import { user } from "$lib/stores/library/user";
import { toAlbum, toArtist, toPlaylist, toSavedAlbum, toSavedTrack, toUserPrivate } from "$lib/utils/converter/spotify";
import { get } from "svelte/store";

export namespace Spotify {

  export async function loadLibraryToStores() {    
    Album.loadSavedToStore();
    Artist.loadFollowedToStore();
    Playlist.loadAllToStore();
    Track.loadSavedToStore();
    User.loadToStore();
  }

  export namespace Album {
    export async function loadSavedToStore() {
      albums.startLoading();
      await loadSavedAlbumsWithTracks(async (album, tracks, total) => {
        albums.setTotal(total);
        albums.addAlbum(toSavedAlbum(album, tracks));
      });
      albums.stopLoading();
    }

    export async function loadAndGet(id: string) {
      const { album, tracks } =  await loadAlbumWithTracks(id);
      return toAlbum(album, tracks)
    }
  }

  export namespace Artist {
    export async function loadFollowedToStore() {
      artists.startLoading();
      await loadFollowedArtists(async (artist, total) => {
        artists.setTotal(total);
        artists.addArtist(toArtist(artist));
      }); 
      artists.stopLoading();
    }

    export async function loadAndGet(id: string) {
      throw "Not Implemented yet.";
    }
  }

  export namespace Playlist {
    export async function loadAllToStore() {
      playlists.startLoading();
      await loadUserPlaylistsWithTracks(async (playlist, tracks, total) => {
        playlists.setTotal(total);
        playlists.addPlaylist(toPlaylist(playlist, tracks));
      });
      playlists.stopLoading();
    }
  }
  
  export namespace Track {
    export async function loadSavedToStore() {
      tracks.startLoading();
      await loadSavedTracks(async (track, total) => {
        tracks.setTotal(total);
        tracks.addTrack(toSavedTrack(track));
      }) 
      tracks.stopLoading();
    }
  }

  export namespace User {
    export async function loadToStore() {
      user.startLoading();
      const u = await laodUser();
      user.setUser(toUserPrivate(u));
      user.stopLoading();
    }
  }
}
