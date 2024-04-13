package dev.kenowi.exportify.authentication.services;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class JwtService {

    private final JWTParser parser;

    public JwtService(JWTParser parser) {
        this.parser = parser;
    }

    public String create(String key, Object value) {
        return Jwt
                .claim(key, value)
                .jwe()
                .encrypt();
    }


    public <T> T parse(String token, String claim) {
        try {
            return parser.parse(token).<T>claim(claim)
                    .orElseThrow(JwtException::new);
        } catch (ParseException e) {
            throw new JwtException(e);
        }
    }

}
