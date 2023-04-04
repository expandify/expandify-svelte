package de.wittenbude.expandify.repositories.albums;

import de.wittenbude.expandify.models.db.AlbumInfo;
import de.wittenbude.expandify.models.db.ArtistInfo;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.artists.ArtistInfoDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlbumInfoDao implements IDao<AlbumInfo> {

    private final AlbumInfoRepository albumInfoRepository;
    private final ArtistInfoDao artistInfoDao;

    public AlbumInfoDao(
            AlbumInfoRepository albumInfoRepository,
            ArtistInfoDao artistInfoDao) {
        this.albumInfoRepository = albumInfoRepository;
        this.artistInfoDao = artistInfoDao;
    }

    @Override
    public Optional<AlbumInfo> find(String id) {
        return albumInfoRepository.findById(id);
    }

    @Override
    public Optional<AlbumInfo> find(AlbumInfo album) {
        return albumInfoRepository.findById(album.getId());
    }

    @Override
    public AlbumInfo save(AlbumInfo album) {
        if (album.getArtists() != null) {
            List<ArtistInfo> artists = artistInfoDao.saveAll(album.getArtists());
            album.setArtists(artists);
        }

        // TODO duplicate key exception if "this.find()" is not there
        // MongoDB considers an Entity new, when a field is "null" or 0 if primitive.
        // Make sure, the most information is saved, whenever a entity is saved
        // orElseGet makes sure the saving is delayed until necessary.
        // orElse does not work, size it is calculated before it is needed
        //
        // return this.find(album).orElseGet(() -> albumInfoRepository.save(album));
        // TODO still relevant?

        return albumInfoRepository.save(album);
    }

    @Override
    public List<AlbumInfo> saveAll(List<AlbumInfo> albumInfos) {
        return albumInfos.stream().map(this::save).toList();
    }
}
