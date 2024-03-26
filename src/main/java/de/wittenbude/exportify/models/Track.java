package de.wittenbude.exportify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}