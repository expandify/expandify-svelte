package de.wittenbude.exportify.plugins

import de.wittenbude.exportify.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        authRoutes()
        authenticate {
            userRouting()
            playlistRouting()
            trackRouting()
            artistRouting()
            albumRouting()
        }
    }
}
