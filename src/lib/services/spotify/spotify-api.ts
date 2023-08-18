import SpotifyWebApi from 'spotify-web-api-js';
import {get} from 'svelte/store';
import {spotifySession} from '$lib/stores/spotifySession';

type LoadingCallbackSavedAlbums = (
    album: SpotifyApi.SavedAlbumObject,
    tracks: SpotifyApi.TrackObjectFull[],
    total: number
) => Promise<void>;
type LoadingCallbackFollowedArtists = (
    artist: SpotifyApi.ArtistObjectFull,
    total: number
) => Promise<void>;
type LoadingCallbackArtistsAlbums = (
    album: SpotifyApi.AlbumObjectSimplified,
    total: number
) => Promise<void>;
type LoadingCallbackPlaylists = (
    playlist: SpotifyApi.PlaylistObjectFull,
    tracks: SpotifyApi.PlaylistTrackObject[],
    total: number
) => Promise<void>;
type LoadingCallbackSavedTrack = (
    track: SpotifyApi.SavedTrackObject,
    total: number
) => Promise<void>;
type LoadingCallbackFullTrack = (track: SpotifyApi.TrackObjectFull, total: number) => Promise<void>;

class SpotifyApi {
    /*******************************
     ******* Generic Request *******
     *******************************/
    async makeRequest<T>(func: (api: SpotifyWebApi.SpotifyWebApiJs) => Promise<T>): Promise<T> {
        const api = new SpotifyWebApi();
        try {
            api.setAccessToken(get(spotifySession)?.token || null);

            return await func(api);
        } catch (error: any) {
            if (error.status !== 429) {
                return Promise.reject({status: error.status, message: 'Error making a request.'});
            }

            const retry = error.getResponseHeader('Retry-After');
            await new Promise((r) => setTimeout(r, Number(retry) * 1000));
            return await func(api);
        }
    }

    /*******************************
     *********** Albums ************
     *******************************/

    async loadAlbumWithTracks(id: string) {
        const album = await this.makeRequest((api) => api.getAlbum(id));
        const tracks = await this.loadAlbumTracks(album);

        return {album: album, tracks: tracks};
    }

    async loadSavedAlbumsWithTracks(callback: LoadingCallbackSavedAlbums) {
        let offset = 0;
        let next: string;

        do {
            const data = await this.makeRequest((api) => api.getMySavedAlbums({limit: 50, offset: offset}));
            offset += data.limit;
            next = data.next;

            for (const album of data.items) {
                const tracks = await this.loadAlbumTracks(album.album);
                await callback(album, tracks, data.total);
            }
        } while (next);
    }

    private async loadAlbumTracks(albumFull: SpotifyApi.AlbumObjectFull) {
        let next = albumFull.tracks.next;
        let offset = albumFull.tracks.items.length;
        let tracks: SpotifyApi.TrackObjectFull[] = [];

        await this.loadSeveralTracks(
            albumFull.tracks.items.map((t) => t.id),
            async (ts) => {
                tracks = [...tracks, ts];
            }
        );

        while (next) {
            const data = await this.makeRequest((api) =>
                api.getAlbumTracks(albumFull.id, {limit: 50, offset})
            );
            offset += data.limit;
            next = data.next;
            await this.loadSeveralTracks(
                data.items.map((t) => t.id),
                async (ts,) => {
                    tracks = [...tracks, ts];
                }
            );
        }

        return tracks;
    }

    /*******************************
     *********** Artists ***********
     *******************************/

    async loadFollowedArtists(callback: LoadingCallbackFollowedArtists) {
        let after: string | null = null;
        let next: string;

        do {
            const data = await this.makeRequest((api) =>
                api.getFollowedArtists({limit: 50, ...(after && {after: after})})
            );
            after = data.artists.cursors.after;
            next = data.artists.next;

            for (const artist of data.artists.items) {
                await callback(artist, data.artists.total || 0);
            }
        } while (next);
    }

    async getArtist(id: string) {
        return await this.makeRequest((api) => api.getArtist(id));
    }

    async getArtistsAlbums(id: string, callback: LoadingCallbackArtistsAlbums) {
        let offset = 0;
        let next: string;

        do {
            const data = await this.makeRequest((api) =>
                api.getArtistAlbums(id, {limit: 50, offset: offset})
            );
            offset += data.limit;
            next = data.next;

            for (const album of data.items) {
                await callback(album, data.total);
            }
        } while (next);
    }

    async getArtistTopTracks(id: string) {
        const response = await this.makeRequest((api) => api.getArtistTopTracks(id, ''));

        return response.tracks;
    }

    async getArtistRelatedArtists(id: string) {
        const response = await this.makeRequest((api) => api.getArtistRelatedArtists(id));

        return response.artists;
    }

    /*******************************
     ********** Playlists ***********
     *******************************/

    async loadUserPlaylistsWithTracks(callback: LoadingCallbackPlaylists) {
        let offset = 0;
        let next: string;

        do {
            // Typescript throws a warning: {} not assignable to string
            // This can be ignored, since the library does some fuckery
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            const data = await this.makeRequest((api) => api.getUserPlaylists({limit: 50, offset: offset}));
            offset += data.limit;
            next = data.next;

            for (const playlist of data.items) {
                const playlistWithTracks = await this.loadPlaylistWithTracks(playlist);
                await callback(playlistWithTracks.playlist, playlistWithTracks.tracks, data.total);
            }
        } while (next);
    }

    private async loadPlaylistWithTracks(playlistSimplified: SpotifyApi.PlaylistObjectSimplified) {
        const playlistFull = await this.makeRequest((api) => api.getPlaylist(playlistSimplified.id));

        let next = playlistFull.tracks.next;
        let offset = 0;
        let tracks: SpotifyApi.PlaylistTrackObject[] = [];

        while (next) {
            const data = await this.makeRequest((api) =>
                api.getPlaylistTracks(playlistSimplified.id, {limit: 50, offset})
            );
            offset += data.limit;
            next = data.next;
            tracks = [...tracks, ...data.items];
        }

        return {playlist: playlistFull, tracks: tracks};
    }

    /*******************************
     *********** Tracks ************
     *******************************/

    async loadSavedTracks(callback: LoadingCallbackSavedTrack) {
        let offset = 0;
        let next: string;

        do {
            const data = await this.makeRequest((api) => api.getMySavedTracks({limit: 50, offset}));
            offset += data.limit;
            next = data.next;

            for (const track of data.items) {
                await callback(track, data.total);
            }
        } while (next);
    }

    async loadSeveralTracks(trackIds: string[], callback: LoadingCallbackFullTrack) {
        const chunkSize = 50;

        for (let i = 0; i < trackIds.length; i += chunkSize) {
            const chunk = trackIds.slice(i, i + chunkSize);

            const data = await this.makeRequest((api) => api.getTracks(chunk));

            for (const track of data.tracks) {
                await callback(track, trackIds.length);
            }
        }
    }

    /*******************************
     ************ User *************
     *******************************/

    async loadUser() {
        return await this.makeRequest((api) => api.getMe());
    }

    /*******************************
     *********** Player ************
     *******************************/

    async getPlaybackState() {
        return await this.makeRequest((api) => api.getMyCurrentPlaybackState());
    }

    async startPlayback() {
        return await this.makeRequest((api) => api.play());
    }

    async pausePlayback() {
        return await this.makeRequest((api) => api.pause());
    }

    async nextPlayback() {
        return await this.makeRequest((api) => api.skipToNext());
    }

    async previousPlayback() {
        return await this.makeRequest((api) => api.skipToPrevious());
    }
}

export const spotifyApi = new SpotifyApi();
