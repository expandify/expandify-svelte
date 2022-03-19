package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.TrackService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.trackRouting() {
    route("/track") {
        get {
            val user = requestUser
            val response = TrackService.getCurrentUserTracks(user)
            call.respond(response)
        }
        post {
            val user = requestUser
            val response = TrackService.saveCurrentUserTracks(user)
            call.respond(response)
        }
    }
}