package de.wittenbude.expandify.services.auth;

import de.wittenbude.expandify.models.spotifydata.SpotifyUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.time.Instant;
import java.util.Map;

@Service
public class JwtService {

    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                // .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claims(cs -> cs.putAll(new JwtClaims(user).toClaims()))
                .build();
        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

        return encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public JwtClaims getClaims(Jwt jwt) {
        return new JwtClaims(jwt.getClaims());
    }


    @Getter
    @Setter
    @ToString
    public class JwtClaims {

        private static final String USER_ID_CLAIM = "userId";
        private String userId;

        public JwtClaims(User user) {
            this.userId = user.getId();
        }
        public JwtClaims(Map<String, Object> claims) {
            this.userId = (String) claims.get(USER_ID_CLAIM);
        }

        public Map<String, Object> toClaims() {
            return Map.of(USER_ID_CLAIM, this.userId);
        }
    }
}
