package de.wittenbude.expandify.repositories.albums;

import de.wittenbude.expandify.models.db.Album;
import de.wittenbude.expandify.models.pojos.SavedAlbum;
import de.wittenbude.expandify.repositories.IDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SavedAlbumDao implements IDao<SavedAlbum> {


    private final AlbumDao albumDao;

    public SavedAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Override
    public Optional<SavedAlbum> find(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<SavedAlbum> find(SavedAlbum album) {
        return albumDao.find(album.getAlbum().getId())
                .map(a -> new SavedAlbum(a, album.getAddedAt()));
    }

    @Override
    public SavedAlbum save(SavedAlbum album) {
        Album saved = albumDao.save(album.getAlbum());
        return new SavedAlbum(saved, album.getAddedAt());
    }

    @Override
    public List<SavedAlbum> saveAll(List<SavedAlbum> albums) {
        return albums.stream().map(this::save).toList();
    }
}
