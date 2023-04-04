package de.wittenbude.expandify.repositories.tracks;

import de.wittenbude.expandify.models.db.*;
import de.wittenbude.expandify.models.pojos.PlaylistTrack;
import de.wittenbude.expandify.repositories.IDao;
import de.wittenbude.expandify.repositories.episodes.EpisodeDao;
import de.wittenbude.expandify.repositories.users.SpotifyUserPublicDao;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.List;
import java.util.Optional;

@Component
public class PlaylistTrackDao implements IDao<PlaylistTrack> {

    private final TrackDao trackDao;
    private final EpisodeDao episodeDao;
    private final SpotifyUserPublicDao spotifyUserPublicDao;

    public PlaylistTrackDao(
            TrackDao trackDao,
            EpisodeDao episodeDao,
            SpotifyUserPublicDao spotifyUserPublicDao) {
        this.trackDao = trackDao;
        this.episodeDao = episodeDao;
        this.spotifyUserPublicDao = spotifyUserPublicDao;
    }

    @Override
    public Optional<PlaylistTrack> find(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<PlaylistTrack> find(PlaylistTrack playlistTrack) {
        if (playlistTrack.getType() == ModelObjectType.TRACK) {
            return trackDao.find(playlistTrack.getTrack())
                    .map(track -> {
                        playlistTrack.setTrack(track);
                        return playlistTrack;
                    });

        }

        if (playlistTrack.getType() == ModelObjectType.EPISODE) {
            return episodeDao.find(playlistTrack.getEpisode())
                    .map(episode -> {
                        playlistTrack.setEpisode(episode);
                        return playlistTrack;
                    });
        }

        return Optional.empty();
    }

    @Override
    public PlaylistTrack save(PlaylistTrack playlistTrack) {
        SpotifyUserPublic owner = spotifyUserPublicDao.save(playlistTrack.getAddedBy());
        playlistTrack.setAddedBy(owner);

        if (playlistTrack.getType() == ModelObjectType.TRACK) {
            Track track = trackDao.save(playlistTrack.getTrack());
            playlistTrack.setTrack(track);
        }

        if (playlistTrack.getType() == ModelObjectType.EPISODE) {
            Episode episode = episodeDao.save(playlistTrack.getEpisode());
            playlistTrack.setEpisode(episode);

        }
        return playlistTrack;
    }

    @Override
    public List<PlaylistTrack> saveAll(List<PlaylistTrack> tracks) {
        return tracks.stream().map(this::save).toList();
    }
}
