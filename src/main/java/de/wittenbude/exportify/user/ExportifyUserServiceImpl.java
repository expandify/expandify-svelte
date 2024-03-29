package de.wittenbude.exportify.user;

import de.wittenbude.exportify.user.api.ExportifyUser;
import de.wittenbude.exportify.user.api.ExportifyUserService;
import org.springframework.stereotype.Service;

@Service
class ExportifyUserServiceImpl implements ExportifyUserService {
    private final ExportifyUserRepository exportifyUserRepository;

    public ExportifyUserServiceImpl(ExportifyUserRepository exportifyUserRepository) {
        this.exportifyUserRepository = exportifyUserRepository;
    }

    public ExportifyUser findOrCreate(String spotifyID) {
        return exportifyUserRepository
                .findBySpotifyID(spotifyID)
                .orElseGet(() -> exportifyUserRepository.save(new ExportifyUser().setSpotifyUserID(spotifyID)));
    }
}
