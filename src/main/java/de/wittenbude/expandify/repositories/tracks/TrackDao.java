package de.wittenbude.expandify.repositories.tracks;

import de.wittenbude.expandify.models.db.AlbumInfo;
import de.wittenbude.expandify.models.db.ArtistInfo;
import de.wittenbude.expandify.models.db.Track;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.albums.AlbumInfoDao;
import de.wittenbude.expandify.repositories.artists.ArtistInfoDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrackDao implements IDao<Track> {

    private final TrackRepository trackRepository;
    private final AlbumInfoDao albumInfoDao;
    private final ArtistInfoDao artistInfoDao;

    public TrackDao(
            TrackRepository trackRepository,
            AlbumInfoDao albumInfoDao,
            ArtistInfoDao artistInfoDao) {
        this.trackRepository = trackRepository;
        this.albumInfoDao = albumInfoDao;
        this.artistInfoDao = artistInfoDao;
    }

    @Override
    public Optional<Track> find(String id) {
        return trackRepository.findById(id);
    }

    @Override
    public Optional<Track> find(Track track) {
        return trackRepository.findById(track.getId());
    }

    @Override
    public Track save(Track track) {
        if (track.getAlbum() != null) {
            AlbumInfo album = albumInfoDao.save(track.getAlbum());
            track.setAlbum(album);
        }

        if (track.getArtists() != null) {
            List<ArtistInfo> artists = artistInfoDao.saveAll(track.getArtists());
            track.setArtists(artists);
        }

        return trackRepository.save(track);
    }

    @Override
    public List<Track> saveAll(List<Track> tracks) {
        return tracks.stream().map(this::save).toList();
    }
}
