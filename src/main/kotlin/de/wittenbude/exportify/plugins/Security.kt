package de.wittenbude.exportify.plugins

import de.wittenbude.exportify.security.ExportifyJWT
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    ExportifyJWT.domain = environment.config.property("jwt.domain").getString()
    ExportifyJWT.audience = environment.config.property("jwt.audience").getString()
    ExportifyJWT.realm = environment.config.property("jwt.realm").getString()
    ExportifyJWT.secret = environment.config.property("jwt.secret").getString()

    authentication {
        jwt {
            realm = ExportifyJWT.realm
            verifier(ExportifyJWT.require())
            validate { credentials -> ExportifyJWT.getPrincipal(credentials) }
        }
    }

}
