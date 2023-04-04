package de.wittenbude.expandify.repositories.tracks;

import de.wittenbude.expandify.models.db.ArtistInfo;
import de.wittenbude.expandify.models.db.TrackInfo;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.artists.ArtistInfoDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrackInfoDao implements IDao<TrackInfo> {

    private final TrackInfoRepository trackInfoRepository;
    private final ArtistInfoDao artistInfoDao;

    public TrackInfoDao(
            TrackInfoRepository trackInfoRepository,
            ArtistInfoDao artistInfoDao) {
        this.trackInfoRepository = trackInfoRepository;
        this.artistInfoDao = artistInfoDao;
    }

    @Override
    public Optional<TrackInfo> find(String id) {
        return trackInfoRepository.findById(id);
    }

    @Override
    public Optional<TrackInfo> find(TrackInfo trackInfo) {
        return trackInfoRepository.findById(trackInfo.getId());
    }

    @Override
    public TrackInfo save(TrackInfo trackInfo) {
        if (trackInfo.getArtists() != null) {
            List<ArtistInfo> artists = artistInfoDao.saveAll(trackInfo.getArtists());
            trackInfo.setArtists(artists);
        }

        return trackInfoRepository.save(trackInfo);
    }

    @Override
    public List<TrackInfo> saveAll(List<TrackInfo> trackInfos) {
        return trackInfos.stream().map(this::save).toList();
    }
}
