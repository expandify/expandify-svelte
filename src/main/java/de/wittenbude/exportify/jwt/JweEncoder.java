package de.wittenbude.exportify.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.TOKEN_TYPE;

@Component
public class JweEncoder {
    private final RSAKey rsaKey;

    public JweEncoder(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    public String encode(Map<String, Object> claims) {
        try {
            Date now = new Date();
            JWTClaimsSet.Builder jwtClaimsBuilder = new JWTClaimsSet
                    .Builder()
                    .issueTime(now)
                    .notBeforeTime(now)
                    .issuer(JwtConfiguration.ISSUER)
                    .audience(JwtConfiguration.AUDIENCE)
                    .expirationTime(new Date(now.getTime() + JwtConfiguration.EXPIRATION.toMillis()))
                    .claim(TOKEN_TYPE, JwtConfiguration.ACCESS_TOKEN_TYPE);
            claims.forEach(jwtClaimsBuilder::claim);

            JWEHeader header = new JWEHeader(JwtConfiguration.JWE_ALGORITHM, JwtConfiguration.ENCRYPTION_METHOD);
            EncryptedJWT jwt = new EncryptedJWT(header, jwtClaimsBuilder.build());
            jwt.encrypt(new RSAEncrypter(rsaKey));

            return jwt.serialize();
        } catch (Exception e) {
            throw new JwtException("Cannot encode token", e);
        }
    }

    public String encode(String key, Object value) {
        return this.encode(Map.of(key, value));
    }
}
