package de.wittenbude.expandify.repositories.libraries;

import de.wittenbude.expandify.models.db.Library;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.albums.SavedAlbumDao;
import de.wittenbude.expandify.repositories.artists.ArtistDao;
import de.wittenbude.expandify.repositories.playlists.PlaylistDao;
import de.wittenbude.expandify.repositories.tracks.SavedTrackDao;
import de.wittenbude.expandify.repositories.users.SpotifyUserPrivateDao;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class LibraryDao implements IDao<Library> {

    private final SavedAlbumDao savedAlbumDao;
    private final ArtistDao artistDao;
    private final PlaylistDao playlistDao;
    private final SavedTrackDao savedTrackDao;
    private final SpotifyUserPrivateDao spotifyUserPrivateDao;

    private final LibraryRepository libraryRepository;

    public LibraryDao(
            SavedAlbumDao savedAlbumDao,
            ArtistDao artistDao,
            PlaylistDao playlistDao,
            SavedTrackDao savedTrackDao,
            SpotifyUserPrivateDao spotifyUserPrivateDao,
            LibraryRepository libraryRepository) {
        this.savedAlbumDao = savedAlbumDao;
        this.artistDao = artistDao;
        this.playlistDao = playlistDao;
        this.savedTrackDao = savedTrackDao;
        this.spotifyUserPrivateDao = spotifyUserPrivateDao;
        this.libraryRepository = libraryRepository;
    }

    public Collection<Library> findAll(String spotifyUserId) {
        return libraryRepository.findAllByOwner_Id(spotifyUserId);
    }

    @Override
    public Optional<Library> find(String id) {
        return libraryRepository.findById(id);
    }

    @Override
    public Optional<Library> find(Library library) {
        return libraryRepository.findById(library.getId());
    }

    @Override
    public Library save(Library library) {
        library.setSavedAlbums(library.getSavedAlbums().stream().map(savedAlbumDao::save).toList());
        library.setFollowedArtists(library.getFollowedArtists().stream().map(artistDao::save).toList());
        library.setPlaylists(library.getPlaylists().stream().map(playlistDao::save).toList());
        library.setSavedTracks(library.getSavedTracks().stream().map(savedTrackDao::save).toList());
        library.setOwner(spotifyUserPrivateDao.save(library.getOwner()));

        return libraryRepository.save(library);
    }

    @Override
    public List<Library> saveAll(List<Library> libraries) {
        return libraries.stream().map(this::save).toList();
    }
}
