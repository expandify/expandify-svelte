package de.wittenbude.exportify.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import de.wittenbude.exportify.models.ExportifyUser
import de.wittenbude.exportify.services.UserService
import io.ktor.server.auth.jwt.*
import java.util.*
import java.util.concurrent.TimeUnit

object ExportifyJWT {
    //default values
    var secret = "secret"
    var domain = "0.0.0.0"
    var audience = "audience"
    var realm = "realm"
    private const val CLAIM_KEY = "id"
    private val DURATION = TimeUnit.HOURS.toMillis(1)


    fun create(id: String): String? {
        return JWT.create()
            .withAudience(audience)
            .withClaim(CLAIM_KEY, id)
            .withIssuer(domain)
            .withExpiresAt(Date(System.currentTimeMillis() + DURATION))
            .sign(Algorithm.HMAC256(secret))
    }

    fun require(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withClaimPresence(CLAIM_KEY)
            .withIssuer(domain)
            .build()
    }

    fun getPrincipal(credential: JWTCredential): ExportifyUser? {
        return credential.payload.claims[CLAIM_KEY]
            ?.asString()
            ?.let { UserService.loadExportifyUser(it) }
    }
}