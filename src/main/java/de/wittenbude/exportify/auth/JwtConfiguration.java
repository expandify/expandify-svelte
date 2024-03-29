package de.wittenbude.exportify.auth;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
class JwtConfiguration {
    public static final JWEAlgorithm JWE_ALGORITHM = JWEAlgorithm.RSA_OAEP_256;
    public static final EncryptionMethod ENCRYPTION_METHOD = EncryptionMethod.A256GCM;
    public static final String ISSUER = "http://localhost:8080/token";
    public static final String ACCESS_TOKEN_TYPE = "access_token";
    public static final String AUDIENCE = "http://localhost:8080/api";
    public static final Duration MAX_CLOCK_SKEW = Duration.of(60, ChronoUnit.SECONDS);
    public static final Duration EXPIRATION = Duration.of(60, ChronoUnit.MINUTES);


    @Bean
    RSAKey encryptionKey() throws JOSEException {
        return new RSAKeyGenerator(2048)
                .keyUse(KeyUse.ENCRYPTION)
                .keyIDFromThumbprint(true)
                .algorithm(JWEAlgorithm.RSA_OAEP_256)
                .generate();
    }


}
