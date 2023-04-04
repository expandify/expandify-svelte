package de.wittenbude.expandify.repositories.playlists;

import de.wittenbude.expandify.models.db.PlaylistInfo;
import de.wittenbude.expandify.models.db.SpotifyUserPublic;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.users.SpotifyUserPublicDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlaylistInfoDao implements IDao<PlaylistInfo> {

    private final PlaylistInfoRepository playlistInfoRepository;
    private final SpotifyUserPublicDao spotifyUserPublicDao;

    public PlaylistInfoDao(
            PlaylistInfoRepository playlistInfoRepository,
            SpotifyUserPublicDao spotifyUserPublicDao) {
        this.playlistInfoRepository = playlistInfoRepository;
        this.spotifyUserPublicDao = spotifyUserPublicDao;
    }

    @Override
    public Optional<PlaylistInfo> find(String id) {
        return playlistInfoRepository.findById(id);
    }

    @Override
    public Optional<PlaylistInfo> find(PlaylistInfo playlist) {
        return playlistInfoRepository.findById(playlist.getId());
    }

    @Override
    public PlaylistInfo save(PlaylistInfo playlistInfo) {
        if (playlistInfo.getOwner() != null) {
            SpotifyUserPublic owner = spotifyUserPublicDao.save(playlistInfo.getOwner());
            playlistInfo.setOwner(owner);
        }

        return playlistInfoRepository.save(playlistInfo);
    }

    @Override
    public List<PlaylistInfo> saveAll(List<PlaylistInfo> playlistInfos) {
        return playlistInfos.stream().map(this::save).toList();
    }
}
