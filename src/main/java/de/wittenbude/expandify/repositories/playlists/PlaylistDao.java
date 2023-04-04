package de.wittenbude.expandify.repositories.playlists;

import de.wittenbude.expandify.models.db.Playlist;
import de.wittenbude.expandify.models.db.SpotifyUserPublic;
import de.wittenbude.expandify.models.pojos.PlaylistTrack;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.tracks.TrackDao;
import de.wittenbude.expandify.repositories.users.SpotifyUserPublicDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlaylistDao implements IDao<Playlist> {

    private final PlaylistRepository playlistRepository;
    private final SpotifyUserPublicDao spotifyUserPublicDao;
    private final TrackDao trackDao;

    public PlaylistDao(
            PlaylistRepository playlistRepository,
            SpotifyUserPublicDao spotifyUserPublicDao,
            TrackDao trackDao) {
        this.playlistRepository = playlistRepository;
        this.spotifyUserPublicDao = spotifyUserPublicDao;
        this.trackDao = trackDao;
    }

    @Override
    public Optional<Playlist> find(String id) {
        return playlistRepository.findById(id);
    }

    @Override
    public Optional<Playlist> find(Playlist playlist) {
        return playlistRepository.findById(playlist.getId());
    }

    @Override
    public Playlist save(Playlist playlist) {
        if (playlist.getTracks() != null) {
            List<PlaylistTrack> tracks = playlist
                    .getTracks()
                    .stream()
                    .peek(p -> p.setTrack(trackDao.save(p.getTrack())))
                    .toList();
            playlist.setTracks(tracks);
        }

        if (playlist.getOwner() != null) {
            SpotifyUserPublic owner = spotifyUserPublicDao.save(playlist.getOwner());
            playlist.setOwner(owner);
        }

        return playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> saveAll(List<Playlist> playlists) {
        return playlists.stream().map(this::save).toList();
    }
}
