package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.getCurrentUserTracks
import de.wittenbude.exportify.services.saveCurrentUserTracks
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.trackRouting() {
    route("/track") {
        get {
            val response = getCurrentUserTracks(requestUser)
            call.respond(response)
        }
        post {
            val response = saveCurrentUserTracks(requestUser)
            call.respond(response)
        }
    }
}