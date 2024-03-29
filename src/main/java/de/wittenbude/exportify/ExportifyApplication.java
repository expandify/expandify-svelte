package de.wittenbude.exportify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients
public class ExportifyApplication {

    // TODO find a way to make "api.schema" not public
    // TODO use @AllArgsConstructor for Schema and SpotifyDate to ensure all fields are set
    // TODO Playlists
    // TODO pagination
    public static void main(String[] args) {
        SpringApplication.run(ExportifyApplication.class, args);
    }

}
