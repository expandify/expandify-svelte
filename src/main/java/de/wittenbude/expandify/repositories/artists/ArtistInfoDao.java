package de.wittenbude.expandify.repositories.artists;

import de.wittenbude.expandify.models.db.ArtistInfo;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ArtistInfoDao implements IDao<ArtistInfo> {

    private final ArtistInfoRepository artistInfoRepository;

    public ArtistInfoDao(ArtistInfoRepository artistInfoRepository) {
        this.artistInfoRepository = artistInfoRepository;
    }

    @Override
    public Optional<ArtistInfo> find(String id) {
        return artistInfoRepository.findById(id);
    }

    @Override
    public Optional<ArtistInfo> find(ArtistInfo artist) {
        return artistInfoRepository.findById(artist.getId());
    }

    @Override
    public ArtistInfo save(ArtistInfo album) {
        return artistInfoRepository.save(album);
    }

    @Override
    public List<ArtistInfo> saveAll(List<ArtistInfo> artistInfos) {
        return artistInfoRepository.saveAll(artistInfos);
    }
}
