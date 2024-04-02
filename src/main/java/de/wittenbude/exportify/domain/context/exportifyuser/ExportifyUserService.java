package de.wittenbude.exportify.domain.context.exportifyuser;


import de.wittenbude.exportify.domain.entities.ExportifyUser;

public interface ExportifyUserService {

    ExportifyUser findOrCreate(String spotifyID);
}
