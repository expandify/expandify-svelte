package de.wittenbude.exportify.routes

import de.wittenbude.exportify.services.authenticate
import de.wittenbude.exportify.services.buildAuthRedirectUri
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    route("") {
        get("/authorize") {
            val authUri = buildAuthRedirectUri()
            call.respondRedirect(authUri)
        }
        get("/callback") {
            val token = authenticate(call)
            call.respond(token?: HttpStatusCode.Unauthorized)
        }
    }
}