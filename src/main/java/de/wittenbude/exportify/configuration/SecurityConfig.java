package de.wittenbude.exportify.configuration;

import de.wittenbude.exportify.jwt.JweDecoder;
import de.wittenbude.exportify.services.ApiTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static de.wittenbude.exportify.controllers.AuthenticationController.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ApiTokenService apiTokenService;
    private final JweDecoder jweDecoder;

    public SecurityConfig(ApiTokenService apiTokenService,
                          JweDecoder jweDecoder) {
        this.apiTokenService = apiTokenService;
        this.jweDecoder = jweDecoder;
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
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jweDecoder::decode)
                                .jwtAuthenticationConverter(apiTokenService::parseApiToken)
                        )
                )
                .build();
    }

}
