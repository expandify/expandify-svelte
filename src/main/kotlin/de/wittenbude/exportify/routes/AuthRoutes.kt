package de.wittenbude.exportify.routes

import de.wittenbude.exportify.services.AuthService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    route("") {
        get("/authorize") {
            val authUri = AuthService.buildAuthRedirectUri()
            call.respondRedirect(authUri)
        }
        get("/callback") {
            val token = AuthService.authenticate(call)
            call.respond(token?: HttpStatusCode.Unauthorized)
        }
    }
}