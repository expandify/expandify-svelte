package de.wittenbude.expandify.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumController.class);

    @GetMapping()
    public String token() {
        LOG.debug("Getting albums");

        return "YEAH!!!";
    }

}
