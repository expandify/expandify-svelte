package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.userRouting() {
    route("/user") {
        get {
            val user = requestUser
            val response = UserService.getCurrentSpotifyUser(user)
            call.respond(response?: HttpStatusCode.InternalServerError)
        }
        post {
            val user = requestUser
            val response = UserService.saveCurrentSpotifyUser(user)
            call.respond(response?: HttpStatusCode.InternalServerError)
        }
    }
}