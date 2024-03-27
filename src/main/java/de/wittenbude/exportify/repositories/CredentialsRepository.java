package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialsRepository extends CrudRepository<Credentials, UUID> {
    Optional<Credentials> findByUser_Id(UUID id);

    default Credentials upsert(Credentials credentials) {
        this.findByUser_Id(credentials.getUser().getId())
                .map(Credentials::getId)
                .ifPresent(credentials::setId);

        return this.save(credentials);

    }
}
