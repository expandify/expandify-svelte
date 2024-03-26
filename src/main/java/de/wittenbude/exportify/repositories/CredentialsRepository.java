package de.wittenbude.exportify.repositories;

import de.wittenbude.exportify.models.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialsRepository extends CrudRepository<Credentials, UUID> {
    Optional<Credentials> findByUser_Id(UUID id);
}
