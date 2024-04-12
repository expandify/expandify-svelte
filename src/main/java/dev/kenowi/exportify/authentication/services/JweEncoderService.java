package dev.kenowi.exportify.authentication.services;

import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import dev.kenowi.exportify.authentication.configuration.JwtConfiguration;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.TOKEN_TYPE;

@Component
public class JweEncoderService {
    private final RSAKey rsaKey;

    public JweEncoderService(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    public String encode(String key, Object value) {
        try {
            Date now = new Date();
            JWTClaimsSet jwtClaims = new JWTClaimsSet
                    .Builder()
                    .issueTime(now)
                    .notBeforeTime(now)
                    .issuer(JwtConfiguration.ISSUER)
                    .audience(JwtConfiguration.AUDIENCE)
                    .expirationTime(new Date(now.getTime() + JwtConfiguration.EXPIRATION.toMillis()))
                    .claim(TOKEN_TYPE, JwtConfiguration.ACCESS_TOKEN_TYPE)
                    .claim(key, value)
                    .build();

            JWEHeader header = new JWEHeader(JwtConfiguration.JWE_ALGORITHM, JwtConfiguration.ENCRYPTION_METHOD);
            EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);
            jwt.encrypt(new RSAEncrypter(rsaKey));

            return jwt.serialize();
        } catch (Exception e) {
            throw new JwtException("Cannot encode token", e);
        }
    }
}
