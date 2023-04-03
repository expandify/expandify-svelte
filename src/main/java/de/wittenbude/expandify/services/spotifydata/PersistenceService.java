package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.models.Library;
import de.wittenbude.expandify.models.spotifydata.*;
import de.wittenbude.expandify.models.spotifydata.helper.PlaylistTrack;
import de.wittenbude.expandify.models.spotifydata.helper.SavedAlbum;
import de.wittenbude.expandify.models.spotifydata.helper.SavedTrack;
import de.wittenbude.expandify.repositories.LibraryRepository;
import de.wittenbude.expandify.repositories.spotifydata.*;
import de.wittenbude.expandify.requestscope.AuthenticatedUserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.List;
import java.util.Optional;

@Service
class PersistenceService {

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceService.class);
    private final AlbumRepository albumRepository;
    private final AlbumSimplifiedRepository albumSimplifiedRepository;
    private final ArtistRepository artistRepository;
    private final ArtistSimplifiedRepository artistSimplifiedRepository;
    private final EpisodeRepository episodeRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistSimplifiedRepository playlistSimplifiedRepository;
    private final ShowSimplifiedRepository showSimplifiedRepository;
    private final SpotifyUserPrivateRepository spotifyUserPrivateRepository;
    private final SpotifyUserPublicRepository spotifyUserPublicRepository;
    private final TrackRepository trackRepository;
    private final TrackSimplifiedRepository trackSimplifiedRepository;
    private final LibraryRepository libraryRepository;

    public PersistenceService(
            AlbumRepository albumRepository,
            AlbumSimplifiedRepository albumSimplifiedRepository,
            ArtistRepository artistRepository,
            ArtistSimplifiedRepository artistSimplifiedRepository,
            EpisodeRepository episodeRepository,
            PlaylistRepository playlistRepository,
            PlaylistSimplifiedRepository playlistSimplifiedRepository,
            ShowSimplifiedRepository showSimplifiedRepository,
            SpotifyUserPrivateRepository spotifyUserPrivateRepository,
            SpotifyUserPublicRepository spotifyUserPublicRepository,
            TrackRepository trackRepository,
            TrackSimplifiedRepository trackSimplifiedRepository,
            LibraryRepository libraryRepository
    ) {
        this.albumRepository = albumRepository;
        this.albumSimplifiedRepository = albumSimplifiedRepository;
        this.artistRepository = artistRepository;
        this.artistSimplifiedRepository = artistSimplifiedRepository;
        this.episodeRepository = episodeRepository;
        this.playlistRepository = playlistRepository;
        this.playlistSimplifiedRepository = playlistSimplifiedRepository;
        this.showSimplifiedRepository = showSimplifiedRepository;
        this.spotifyUserPrivateRepository = spotifyUserPrivateRepository;
        this.spotifyUserPublicRepository = spotifyUserPublicRepository;
        this.trackRepository = trackRepository;
        this.trackSimplifiedRepository = trackSimplifiedRepository;
        this.libraryRepository = libraryRepository;
    }

    public Album save(Album album) {
        if (album.getTracks() != null) {
            List<TrackSimplified> tracks = album.getTracks().stream().map(this::save).toList();
            album.setTracks(tracks);
        }

        if (album.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(album.getArtists());
            album.setArtists(artists);
        }

        return albumRepository.save(album);
    }


    public Optional<Album> find(Album album) {
        return albumRepository.findById(album.getId());
    }

    public Optional<Album> findAlbum(String id) {
        return albumRepository.findById(id);
    }

    public AlbumSimplified save(AlbumSimplified album) {
        if (album.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(album.getArtists());
            album.setArtists(artists);
        }

        // TODO duplicate key exception if "this.find()" is not there
        // MongoDB considers an Entity new, when a field is "null" or 0 if primitive.
        // Make sure, the most information is saved, whenever a entity is saved
        // orElseGet makes sure the saving is delayed until necessary.
        // orElse does not work, size it is calculated before it is needed
        return this.find(album).orElseGet(() -> albumSimplifiedRepository.save(album));
    }

    public Optional<AlbumSimplified> find(AlbumSimplified album) {
        return albumSimplifiedRepository.findById(album.getId());
    }

    public SavedAlbum save(SavedAlbum album) {
        Album saved = this.save(album.getAlbum());
        return new SavedAlbum(saved, album.getAddedAt());
    }

    public Optional<SavedAlbum> find(SavedAlbum album) {
        return albumRepository.findById(album.getAlbum().getId())
                .map(a -> new SavedAlbum(a, album.getAddedAt()));
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public Optional<Artist> find(Artist artist) {
        return artistRepository.findById(artist.getId());
    }

    public Optional<Artist> findArtist(String id) {
        return artistRepository.findById(id);
    }

    public ArtistSimplified save(ArtistSimplified artist) {
        return artistSimplifiedRepository.save(artist);
    }

    public Optional<ArtistSimplified> find(ArtistSimplified artistSimplified) {
        return artistSimplifiedRepository.findById(artistSimplified.getId());
    }

    private List<ArtistSimplified> saveAll(List<ArtistSimplified> artists) {
        return artistSimplifiedRepository.saveAll(artists);
    }

    public Episode save(Episode episode) {
        if (episode.getShow() != null) {
            ShowSimplified show = this.save(episode.getShow());
            episode.setShow(show);
        }
        return episodeRepository.save(episode);
    }

    public Optional<Episode> find(Episode episode) {
        return episodeRepository.findById(episode.getId());
    }

    public PlaylistSimplified save(PlaylistSimplified playlistSimplified) {
        if (playlistSimplified.getOwner() != null) {
            SpotifyUserPublic owner = this.save(playlistSimplified.getOwner());
            playlistSimplified.setOwner(owner);
        }

        if (playlistSimplified.getTracks() != null) {
            List<PlaylistTrack> tracks = playlistSimplified.getTracks().stream().map(this::save).toList();
            playlistSimplified.setTracks(tracks);
        }

        return playlistSimplifiedRepository.save(playlistSimplified);
    }

    public Optional<PlaylistSimplified> find(PlaylistSimplified playlistSimplified) {
        return playlistSimplifiedRepository.findById(playlistSimplified.getId());
    }

    public Playlist save(Playlist playlist) {
        if (playlist.getTracks() != null) {
            List<PlaylistTrack> tracks = playlist.getTracks().stream().map(this::save).toList();
            playlist.setTracks(tracks);
        }

        if (playlist.getOwner() != null) {
            SpotifyUserPublic owner = this.save(playlist.getOwner());
            playlist.setOwner(owner);
        }

        return playlistRepository.save(playlist);
    }

    public Optional<Playlist> find(Playlist playlist) {
        return playlistRepository.findById(playlist.getId());
    }

    public Optional<Playlist> findPlaylist(String id) {
        return playlistRepository.findById(id);
    }

    public ShowSimplified save(ShowSimplified showSimplified) {
        return showSimplifiedRepository.save(showSimplified);
    }

    public Optional<ShowSimplified> find(ShowSimplified showSimplified) {
        return showSimplifiedRepository.findById(showSimplified.getId());
    }

    public Optional<SpotifyUserPrivate> find(AuthenticatedUserData authenticatedUserData) {
        return spotifyUserPrivateRepository.findById(authenticatedUserData.getSpotifyUserId());
    }

    public SpotifyUserPrivate save(SpotifyUserPrivate spotifyUser) {
        // TODO duplicate key exception if "this.find()" is not there
        // MongoDB considers an Entity new, when a field is "null" or 0 if primitive.
        // Make sure, the most information is saved, whenever a entity is saved
        // orElseGet makes sure the saving is delayed until necessary.
        // orElse does not work, size it is calculated before it is needed
        return this.find(spotifyUser)
                .orElseGet(() -> spotifyUserPrivateRepository.save(spotifyUser));
    }

    public Optional<SpotifyUserPrivate> findSpotifyUserPrivate(String id) {
        return spotifyUserPrivateRepository.findById(id);
    }

    public Optional<SpotifyUserPrivate> find(SpotifyUserPrivate spotifyUser) {
        return spotifyUserPrivateRepository.findById(spotifyUser.getId());
    }

    public SpotifyUserPublic save(SpotifyUserPublic spotifyUser) {
        // TODO duplicate key exception if "this.find()" is not there
        // MongoDB considers an Entity new, when a field is "null" or 0 if primitive.
        // Make sure, the most information is saved, whenever a entity is saved
        // orElseGet makes sure the saving is delayed until necessary.
        // orElse does not work, size it is calculated before it is needed
        return this.find(spotifyUser).orElseGet(() -> spotifyUserPublicRepository.save(spotifyUser));
    }

    public Optional<SpotifyUserPublic> findSpotifyUserPublic(String id) {
        return spotifyUserPublicRepository.findById(id);
    }

    public Optional<SpotifyUserPublic> find(SpotifyUserPublic spotifyUser) {
        return spotifyUserPublicRepository.findById(spotifyUser.getId());
    }

    public Track save(Track track) {
        if (track.getAlbum() != null) {
            AlbumSimplified album = this.save(track.getAlbum());
            track.setAlbum(album);
        }

        if (track.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(track.getArtists());
            track.setArtists(artists);
        }

        return trackRepository.save(track);
    }

    public Optional<Track> find(Track track) {
        return trackRepository.findById(track.getId());
    }

    public Optional<Track> findTrack(String id) {
        return trackRepository.findById(id);
    }

    public TrackSimplified save(TrackSimplified trackSimplified) {
        if (trackSimplified.getArtists() != null) {
            List<ArtistSimplified> artists = this.saveAll(trackSimplified.getArtists());
            trackSimplified.setArtists(artists);
        }

        return trackSimplifiedRepository.save(trackSimplified);
    }

    public Optional<TrackSimplified> find(TrackSimplified trackSimplified) {
        return trackSimplifiedRepository.findById(trackSimplified.getId());
    }

    public PlaylistTrack save(PlaylistTrack playlistTrack) {
        SpotifyUserPublic owner = this.save(playlistTrack.getAddedBy());
        playlistTrack.setAddedBy(owner);

        if (playlistTrack.getType() == ModelObjectType.TRACK) {
            Track track = this.save(playlistTrack.getTrack());
            playlistTrack.setTrack(track);
        }

        if (playlistTrack.getType() == ModelObjectType.EPISODE) {
            Episode episode = this.save(playlistTrack.getEpisode());
            playlistTrack.setEpisode(episode);

        }
        return playlistTrack;
    }

    public Optional<PlaylistTrack> find(PlaylistTrack playlistTrack) {
        if (playlistTrack.getType() == ModelObjectType.TRACK) {
            return this.find(playlistTrack.getTrack())
                    .map(track -> {
                        playlistTrack.setTrack(track);
                        return playlistTrack;
                    });

        }

        if (playlistTrack.getType() == ModelObjectType.EPISODE) {
            return this.find(playlistTrack.getEpisode())
                    .map(episode -> {
                        playlistTrack.setEpisode(episode);
                        return playlistTrack;
                    });
        }

        return Optional.empty();
    }

    public SavedTrack save(SavedTrack savedTrack) {
        return new SavedTrack(
                this.save(savedTrack.getTrack()),
                savedTrack.getAddedAt()
        );
    }

    public Optional<SavedTrack> find(SavedTrack savedTrack) {
        return this.find(savedTrack.getTrack())
                .map(track -> new SavedTrack(track, savedTrack.getAddedAt()));
    }

    public Library save(Library library) {

        library.setSavedAlbums(library.getSavedAlbums().stream().map(this::save).toList());
        library.setFollowedArtists(library.getFollowedArtists().stream().map(this::save).toList());
        library.setPlaylists(library.getPlaylists().stream().map(this::save).toList());
        library.setSavedTracks(library.getSavedTracks().stream().map(this::save).toList());
        library.setOwner(this.save(library.getOwner()));

        return libraryRepository.save(library);
    }

    public List<Library> findLibraries(String spotifyUserId) {
        return libraryRepository.findAllByOwner_Id(spotifyUserId);
    }

    public Optional<Library> findLibrary(String id) {
        return libraryRepository.findById(id);
    }
}
