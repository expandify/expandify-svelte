package de.wittenbude.expandify.repositories.tracks;

import de.wittenbude.expandify.models.pojos.SavedTrack;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SavedTrackDao implements IDao<SavedTrack> {

    private final TrackDao trackDao;


    public SavedTrackDao(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    public Optional<SavedTrack> find(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<SavedTrack> find(SavedTrack track) {
        return trackDao.find(track.getTrack())
                .map(t -> new SavedTrack(t, track.getAddedAt()));
    }

    @Override
    public SavedTrack save(SavedTrack savedTrack) {
        return new SavedTrack(
                trackDao.save(savedTrack.getTrack()),
                savedTrack.getAddedAt()
        );
    }

    @Override
    public List<SavedTrack> saveAll(List<SavedTrack> tracks) {
        return tracks.stream().map(this::save).toList();
    }
}
