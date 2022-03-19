package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.PlaylistService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playlistRouting() {
    route("/playlist") {
        get {
            val user = requestUser
            val response = PlaylistService.getUserPlaylists(user)
            call.respond(response)
        }
        post {
            val user = requestUser
            val response = PlaylistService.saveUserPlaylists(user)
            call.respond(response)
        }
    }
}