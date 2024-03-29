package de.wittenbude.exportify.album;

import de.wittenbude.exportify.album.api.Album;
import de.wittenbude.exportify.album.api.AlbumService;
import de.wittenbude.exportify.album.api.SavedAlbum;
import de.wittenbude.exportify.artist.api.Artist;
import de.wittenbude.exportify.artist.api.ArtistService;
import de.wittenbude.exportify.spotify.clients.SpotifyAlbumClient;
import de.wittenbude.exportify.spotify.data.SpotifyPage;
import de.wittenbude.exportify.track.api.Track;
import de.wittenbude.exportify.track.api.TrackService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
class AlbumServiceImpl implements AlbumService {

    private final SpotifyAlbumClient spotifyAlbumClient;
    private final AlbumRepository albumRepository;
    private final ArtistService artistService;
    private final TrackService trackService;

    public AlbumServiceImpl(SpotifyAlbumClient spotifyAlbumClient,
                            AlbumRepository albumRepository,
                            ArtistService artistService,
                            TrackService trackService) {
        this.spotifyAlbumClient = spotifyAlbumClient;
        this.albumRepository = albumRepository;
        this.artistService = artistService;
        this.trackService = trackService;
    }


    public Stream<SavedAlbum> loadSavedAlbums() {
        return SpotifyPage
                .streamPagination(offset -> spotifyAlbumClient.getSaved(50, offset))
                .map(spotifySavedAlbum -> {
                    List<String> artistIDs = artistService
                            .loadArtists(spotifySavedAlbum.getAlbum().getArtists())
                            .map(Artist::getSpotifyID)
                            .toList();

                    List<String> trackIDs = trackService
                            .loadAlbumTracks(spotifySavedAlbum.getAlbum().getId())
                            .map(Track::getSpotifyID)
                            .toList();

                    Album album = spotifySavedAlbum.getAlbum().convert(artistIDs, trackIDs);
                    Album savedAlbum = albumRepository.upsert(album);

                    return new SavedAlbum()
                            .setSavedAt(spotifySavedAlbum.getAddedAt().toInstant())
                            .setAlbum(savedAlbum);
                });
    }

}
