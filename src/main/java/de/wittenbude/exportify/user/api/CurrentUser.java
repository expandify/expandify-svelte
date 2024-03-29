package de.wittenbude.exportify.user.api;

import java.util.UUID;

public interface CurrentUser {
    UUID getID();

    ExportifyUser getUser();
}
