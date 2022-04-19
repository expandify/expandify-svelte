package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.getFollowedArtists
import de.wittenbude.exportify.services.saveFollowedArtists
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.artistRouting() {
    route("/artist") {
        get {
            val response = getFollowedArtists(requestUser)
            call.respond(response)
        }
        post {
            val response = saveFollowedArtists(requestUser)
            call.respond(response)
        }
    }
}