import Dexie, { type Table } from 'dexie';
import type { Artist, Playlist, SavedAlbum, SavedTrack, UserPrivate } from "$lib/types/spotify";
import { albums } from "$lib/stores/library/albums";
import { toArtist, toPlaylist, toSavedAlbum, toSavedTrack, toUserPrivate } from "$lib/utils/converter/spotify";
import { notifications } from "$lib/stores/notifications";
import { get } from "svelte/store";
import { playlists } from "$lib/stores/library/playlists";
import { artists } from "$lib/stores/library/artists";
import { tracks } from "$lib/stores/library/tracks";
import { user } from "$lib/stores/library/user";
import { spotifyApi } from "$lib/services/spotify/spotify-api";


class DexieIndexDB extends Dexie {
  albums!: Table<SavedAlbum>;
  artists!: Table<Artist>;
  playlists!: Table<Playlist>;
  tracks!: Table<SavedTrack>;
  user!: Table<UserPrivate>;

  constructor() {
    super('expandify');
    this.version(1).stores({
      albums: '++id', // Primary key and indexed props
      artists: '++id',
      playlists: '++id',
      tracks: '++id',
      user: '++id'
    });
  }
}

class SpotifyPersistence {
  db: DexieIndexDB;

  constructor() {
    this.db = new DexieIndexDB();
  }

  /*******************************
   ************ Load *************
   *******************************/
  /* Loads from the DB if possible otherwise from spotify. */


  async loadLibrary() {
    return Promise.all([
      this.loadSavedAlbums(),
      this.loadFollowedArtists(),
      this.loadPlaylists(),
      this.loadSavedTracks(),
      this.loadUser()
    ]);
  }


  private async loadAlbumsFromDB() {
    let dbError = false;
    const size = await this.db.albums.count();
    if (size > 0) {
      albums.setTotal(size)
      await this.db.albums.toArray()
        .then(as => albums.addAlbums(as))
        .catch( () => {
          dbError = true;
        });
      return !dbError;
    } else {
      return false;
    }
  }

  private async loadArtistsFromDB() {
    let dbError = false;
    const size = await this.db.artists.count();
    if (size > 0) {
      artists.setTotal(size)
      await this.db.artists.toArray()
        .then(as => artists.addArtists(as))
        .catch( () => {
          dbError = true;
        });
      return !dbError;
    } else {
      return false;
    }
  }

  private async loadPlaylistsFromDB() {
    let dbError = false;
    const size = await this.db.playlists.count();
    if (size > 0) {
      playlists.setTotal(size)
      await this.db.playlists.toArray()
        .then(ps => playlists.addPlaylists(ps))
        .catch( () => {
          dbError = true;
        });
      return !dbError;
    } else {
      return false;
    }
  }

  private async loadTracksFromDB() {
    let dbError = false;
    const size = await this.db.tracks.count();
    if (size > 0) {
      tracks.setTotal(size)
      await this.db.tracks.toArray()
        .then(ts => tracks.addTracks(ts))
        .catch( () => {
        dbError = true;
      });
      return !dbError;
    } else {
      return false;
    }
  }

  private async loadUserFromDB() {
    let dbError = false;
    const size = await this.db.user.count();
    if (size > 0) {
      this.db.user.each((u) => {
        user.setUser(u)
      }).catch( () => {
        dbError = true;
      });
      return !dbError;
    } else {
      return false;
    }
  }

  async loadSavedAlbums() {
    albums.startLoading();

    if (await this.loadAlbumsFromDB()) {
      albums.stopLoading();
      return;
    }

    await spotifyApi.loadSavedAlbumsWithTracks(async (album, tracks, total) => {
      albums.setTotal(total);
      const a = toSavedAlbum(album, tracks);
      albums.addAlbum(a);
      this.db.albums.add(a);
    }).catch((reason) => {
      const msg = "Error loading albums";
      albums.setError({cause: reason, message: msg});
      notifications.addError(msg);
    });
    albums.stopLoading();
    if (!get(albums).error) { notifications.addSuccess("Finished loading albums")}
  }

  async loadFollowedArtists() {
    artists.startLoading();

    if (await this.loadArtistsFromDB()) {
      artists.stopLoading();
      return;
    }

    await spotifyApi.loadFollowedArtists(async (artist, total) => {
      artists.setTotal(total);
      const a = toArtist(artist);
      artists.addArtist(a);
      this.db.artists.add(a);
    }).catch((reason) => {
      const msg = "Error loading artists";
      artists.setError({cause: reason, message: msg});
      notifications.addError(msg);
    });
    artists.stopLoading();
    if (!get(artists).error) { notifications.addSuccess("Finished loading artists")}
  }


  async loadPlaylists() {
    playlists.startLoading();

    if (await this.loadPlaylistsFromDB()) {
      playlists.stopLoading();
      return;
    }

    await spotifyApi.loadUserPlaylistsWithTracks(async (playlist, tracks, total) => {
      playlists.setTotal(total);
      const p = toPlaylist(playlist, tracks);
      playlists.addPlaylist(p);
      this.db.playlists.add(p);
    }).catch((reason) => {
      const msg = "Error loading playlists";
      playlists.setError({cause: reason, message: msg});
      console.log(reason)
      notifications.addError(msg);
    });
    playlists.stopLoading();
    if (!get(playlists).error) { notifications.addSuccess("Finished loading playlists")}
  }

  async loadSavedTracks() {
    tracks.startLoading();

    if (await this.loadTracksFromDB()) {
      tracks.stopLoading();
      return;
    }

    await spotifyApi.loadSavedTracks(async (track, total) => {
      tracks.setTotal(total);
      const t = toSavedTrack(track);
      tracks.addTrack(t);
      this.db.tracks.add(t);
    }).catch((reason) => {
      const msg = "Error loading tracks";
      tracks.setError({cause: reason, message: msg});
      notifications.addError(msg);
    });
    tracks.stopLoading();
    if (!get(tracks).error) { notifications.addSuccess("Finished loading tracks")}
  }

  async loadUser() {
    user.startLoading();

    if (await this.loadUserFromDB()) {
      user.stopLoading();
      return;
    }

    try {
      const u = toUserPrivate(await spotifyApi.loadUser());
      user.setUser(u);
      this.db.user.add(u);
    } catch (error) {
      const msg = "Error loading user";
      user.setError({cause: error, message: msg});
      notifications.addError(msg);
    }
    user.stopLoading();
    if (!get(user).error) { notifications.addSuccess("Finished loading user")}
  }

  /*******************************
   *********** Reload ************
   *******************************/
  /* Loads from spotify and updates the db. */

  async reloadLibrary() {
    return await Promise.all([
      this.reloadSavedAlbums(),
      this.reloadFollowedArtists(),
      this.reloadPlaylists(),
      this.reloadSavedTracks(),
      this.reloadUser()
    ]);
  }

  async reloadSavedAlbums() {
    await this.db.albums.clear()
    await this.loadSavedAlbums();
  }

  async reloadFollowedArtists() {
    await this.db.artists.clear()
    await this.loadFollowedArtists();
  }

  async reloadPlaylists() {
    await this.db.playlists.clear()
    await this.loadPlaylists();
  }

  async reloadSavedTracks() {
    await this.db.tracks.clear()
    await this.loadSavedTracks();
  }

  async reloadUser() {
    await this.db.user.clear()
    await this.loadUser();
  }
}

export const spotifyPersistence= new SpotifyPersistence();