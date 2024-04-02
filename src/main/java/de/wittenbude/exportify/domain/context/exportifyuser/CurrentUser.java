package de.wittenbude.exportify.domain.context.exportifyuser;

import de.wittenbude.exportify.domain.entities.ExportifyUser;

import java.util.UUID;

public interface CurrentUser {
    UUID getID();

    ExportifyUser getUser();
}
