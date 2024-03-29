package de.wittenbude.exportify.user.api;

public interface ExportifyUserService {
    ExportifyUser findOrCreate(String spotifyID);
}
