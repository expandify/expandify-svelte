package de.wittenbude.expandify.repositories.albums;

import de.wittenbude.expandify.models.db.Album;
import de.wittenbude.expandify.models.db.ArtistInfo;
import de.wittenbude.expandify.models.db.TrackInfo;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.artists.ArtistInfoDao;
import de.wittenbude.expandify.repositories.tracks.TrackInfoDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlbumDao implements IDao<Album> {

    private final AlbumRepository albumRepository;
    private final TrackInfoDao trackInfoDao;
    private final ArtistInfoDao artistInfoDao;

    public AlbumDao(
            AlbumRepository albumRepository,
            TrackInfoDao trackInfoDao,
            ArtistInfoDao artistInfoDao) {
        this.albumRepository = albumRepository;
        this.trackInfoDao = trackInfoDao;
        this.artistInfoDao = artistInfoDao;
    }

    @Override
    public Optional<Album> find(String id) {
        return albumRepository.findById(id);
    }

    @Override
    public Optional<Album> find(Album album) {
        return albumRepository.findById(album.getId());
    }

    @Override
    public Album save(Album album) {
        if (album.getTracks() != null) {
            List<TrackInfo> tracks = album.getTracks().stream().map(trackInfoDao::save).toList();
            album.setTracks(tracks);
        }

        if (album.getArtists() != null) {
            List<ArtistInfo> artists = artistInfoDao.saveAll(album.getArtists());
            album.setArtists(artists);
        }

        return albumRepository.save(album);
    }

    @Override
    public List<Album> saveAll(List<Album> albums) {
        return albums.stream().map(this::save).toList();
    }
}
