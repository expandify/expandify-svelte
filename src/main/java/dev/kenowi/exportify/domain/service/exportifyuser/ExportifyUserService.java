package dev.kenowi.exportify.domain.service.exportifyuser;

import dev.kenowi.exportify.domain.entities.ExportifyUser;
import dev.kenowi.exportify.domain.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExportifyUserService {

    private final ExportifyUserRepository exportifyUserRepository;

    ExportifyUserService(ExportifyUserRepository exportifyUserRepository) {
        this.exportifyUserRepository = exportifyUserRepository;
    }


    public ExportifyUser findOrCreate(String spotifyID) {
        return exportifyUserRepository
                .findBySpotifyID(spotifyID)
                .orElseGet(() -> exportifyUserRepository.save(new ExportifyUser().setSpotifyUserID(spotifyID)));
    }

    public ExportifyUser mustFindByID(UUID userID) {
        return exportifyUserRepository
                .findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("current user not found"));
    }
}
