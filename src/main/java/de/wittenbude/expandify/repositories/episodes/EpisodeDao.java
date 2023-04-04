package de.wittenbude.expandify.repositories.episodes;

import de.wittenbude.expandify.models.db.Episode;
import de.wittenbude.expandify.models.db.ShowInfo;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.shows.ShowInfoDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EpisodeDao implements IDao<Episode> {

    private final EpisodeRepository episodeRepository;
    private final ShowInfoDao showInfoDao;

    public EpisodeDao(
            EpisodeRepository episodeRepository,
            ShowInfoDao showInfoDao) {
        this.episodeRepository = episodeRepository;
        this.showInfoDao = showInfoDao;
    }

    @Override
    public Optional<Episode> find(String id) {
        return episodeRepository.findById(id);
    }

    @Override
    public Optional<Episode> find(Episode episode) {
        return episodeRepository.findById(episode.getId());
    }

    @Override
    public Episode save(Episode episode) {
        if (episode.getShow() != null) {
            ShowInfo show = showInfoDao.save(episode.getShow());
            episode.setShow(show);
        }
        return episodeRepository.save(episode);
    }

    @Override
    public List<Episode> saveAll(List<Episode> episodes) {
        return episodes.stream().map(this::save).toList();
    }
}
