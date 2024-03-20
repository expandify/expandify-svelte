package de.wittenbude.exportify.db.repositories;

import de.wittenbude.exportify.db.entity.CredentialsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialsRepository extends CrudRepository<CredentialsEntity, UUID> {
    Optional<CredentialsEntity> findByUserEntity_Id(UUID id);
}
