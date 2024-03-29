package de.wittenbude.exportify;

import de.wittenbude.exportify.auth.api.ApiTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static final String AUTHORIZATION_PATH = "/authorize";
    public static final String AUTHORIZATION_CALLBACK_PATH = "/authorize/callback";
    public static final String AUTHENTICATION_TOKEN_PATH = "/authorize/token";

    private final ApiTokenService apiTokenService;

    public SecurityConfig(ApiTokenService apiTokenService) {
        this.apiTokenService = apiTokenService;
    }

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(config -> config
                        .requestMatchers(AUTHORIZATION_PATH).permitAll()
                        .requestMatchers(AUTHORIZATION_CALLBACK_PATH).permitAll()
                        .requestMatchers(AUTHENTICATION_TOKEN_PATH).permitAll()

                        //TODO Actuator Security
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(apiTokenService::configureTokenParsing))
                .build();
    }

}
