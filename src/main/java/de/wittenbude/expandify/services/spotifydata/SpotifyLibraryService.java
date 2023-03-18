package de.wittenbude.expandify.services.spotifydata;

import de.wittenbude.expandify.repositories.LibraryRepository;
import de.wittenbude.expandify.services.spotifyapi.SpotifyApiRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyLibraryService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotifyLibraryService.class);
    private final LibraryRepository libraryRepository;
    private final SpotifyApiRequestService spotifyApiRequest;

    public SpotifyLibraryService(LibraryRepository libraryRepository, SpotifyApiRequestService spotifyApiRequest) {
        this.libraryRepository = libraryRepository;
        this.spotifyApiRequest = spotifyApiRequest;
    }
}
