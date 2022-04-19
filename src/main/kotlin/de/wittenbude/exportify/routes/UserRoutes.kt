package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.getCurrentSpotifyUser
import de.wittenbude.exportify.services.saveCurrentSpotifyUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.userRouting() {
    route("/user") {
        get {
            val response = getCurrentSpotifyUser(requestUser)
            call.respond(response?: HttpStatusCode.InternalServerError)
        }
        post {
            val response = saveCurrentSpotifyUser(requestUser)
            call.respond(response?: HttpStatusCode.InternalServerError)
        }
    }
}