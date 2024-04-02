package de.wittenbude.exportify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExportifyApplication {

    // TODO find a way to make "api.schema" not public
    // TODO Playlists
    public static void main(String[] args) {
        SpringApplication.run(ExportifyApplication.class, args);
    }

}
