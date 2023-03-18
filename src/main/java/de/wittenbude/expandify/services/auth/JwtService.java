package de.wittenbude.expandify.services.auth;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.User;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;

@Service
public class JwtService {

    private static final String USER_ID_CLAIM = "userId";
    @Value("${jwt.secret.key}")
    private String secretKey;
    private final JwtEncoder encoder;
    @Getter
    private final JwtDecoder decoder;

    public JwtService() {
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>(key);
        this.encoder = new NimbusJwtEncoder(immutableSecret);
        this.decoder = NimbusJwtDecoder.withSecretKey(key).build();

    }

    public String generateToken(String userId) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                // .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claims(cs -> cs.put(USER_ID_CLAIM, userId))
                .build();
        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

        return encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String getUserIdFromJwt(Jwt jwt) {
        return (String) jwt.getClaims().get(USER_ID_CLAIM);
    }

}
