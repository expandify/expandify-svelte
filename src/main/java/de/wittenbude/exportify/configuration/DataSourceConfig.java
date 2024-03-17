package de.wittenbude.exportify.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)

                //.setName("Exportify")
                .setScriptEncoding("UTF-8")
                //.ignoreFailedDrops(true)
                //.addScript("schema.sql")
                //.addScripts("user_data.sql", "country_data.sql")
                .build();
    }

}
