package dev.kenowi.exportify.domain.service.jwt;

import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.EncryptedJWT;
import dev.kenowi.exportify.application.configuration.JwtConfiguration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.*;

@Service
public class JweDecoderService {

    private final RSAKey rsaKey;
    private final OAuth2TokenValidator<Jwt> validator;

    public JweDecoderService(RSAKey rsaKey) {
        this.rsaKey = rsaKey;

        List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
        validators.add(new JwtTimestampValidator(JwtConfiguration.MAX_CLOCK_SKEW));
        validators.add(new JwtIssuerValidator(JwtConfiguration.ISSUER));
        validators.add(new JwtClaimValidator<String>(AUD, JwtConfiguration.AUDIENCE::equals));
        validators.add(new JwtClaimValidator<Instant>(NBF, nbf -> Instant.now().isAfter(nbf.minus(JwtConfiguration.MAX_CLOCK_SKEW))));
        validators.add(new JwtClaimValidator<Instant>(IAT, iat -> Instant.now().isAfter(iat.minus(JwtConfiguration.MAX_CLOCK_SKEW))));
        validators.add(new JwtClaimValidator<String>(TOKEN_TYPE, JwtConfiguration.ACCESS_TOKEN_TYPE::equals));
        this.validator = new DelegatingOAuth2TokenValidator<>(validators);
    }

    public String decode(String token, String key) {
        return this.decode(token).getClaim(key);
    }

    public Jwt decode(String token) {
        try {
            EncryptedJWT encryptedJWT = EncryptedJWT.parse(token);
            RSADecrypter decrypter = new RSADecrypter(rsaKey);
            encryptedJWT.decrypt(decrypter);

            Map<String, Object> headers = encryptedJWT.getHeader().toJSONObject();
            Map<String, Object> claims = encryptedJWT.getPayload().toJSONObject();

            Jwt jwt = Jwt.withTokenValue(token)
                    .headers((h) -> h.putAll(headers))
                    .claims((c) -> c.putAll(claims))
                    .notBefore(toInstant(claims.get(NBF)))
                    .issuedAt(toInstant(claims.get(IAT)))
                    .expiresAt(toInstant(claims.get(EXP)))
                    .build();

            return validateJwt(jwt);
        } catch (Exception e) {
            throw new JwtException("Cannot decode token", e);
        }
    }

    private Jwt validateJwt(Jwt jwt) {
        OAuth2TokenValidatorResult result = this.validator.validate(jwt);
        if (result.hasErrors()) {
            Collection<OAuth2Error> errors = result.getErrors();
            String validationErrorString = getJwtValidationExceptionMessage(errors);
            throw new JwtValidationException(validationErrorString, errors);
        }
        return jwt;
    }

    private String getJwtValidationExceptionMessage(Collection<OAuth2Error> errors) {
        for (OAuth2Error oAuth2Error : errors) {
            if (StringUtils.hasLength(oAuth2Error.getDescription())) {
                return String.format("An error occurred while attempting to decode the Jwt: %s", oAuth2Error.getDescription());
            }
        }
        return "Unable to validate Jwt";
    }

    private Instant toInstant(Object claim) {
        if (claim == null) {
            return null;
        }
        return Instant.ofEpochSecond((Long) claim);
    }
}
