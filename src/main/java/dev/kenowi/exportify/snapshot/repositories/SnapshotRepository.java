package dev.kenowi.exportify.snapshot.repositories;

import dev.kenowi.exportify.snapshot.entities.Snapshot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SnapshotRepository {
    private final EntityManager entityManager;

    public SnapshotRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public Optional<Snapshot> findByID(UUID id) {
        return Optional.ofNullable(entityManager.find(Snapshot.class, id));
    }

    @Transactional
    public Snapshot save(Snapshot snapshot) {
        if (snapshot.getId() == null) {
            entityManager.persist(snapshot);
            return snapshot;
        } else {
            return entityManager.merge(snapshot);
        }
    }
}