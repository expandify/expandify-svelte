package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.getUserPlaylists
import de.wittenbude.exportify.services.saveUserPlaylists
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playlistRouting() {
    route("/playlist") {
        get {
            val response = getUserPlaylists(requestUser)
            call.respond(response)
        }
        post {
            val response = saveUserPlaylists(requestUser)
            call.respond(response)
        }
    }
}