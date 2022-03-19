package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.ArtistService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.artistRouting() {
    route("/artist") {
        get {
            val user = requestUser
            val response = ArtistService.getFollowedArtists(user)
            call.respond(response)
        }
        post {
            val user = requestUser
            val response = ArtistService.saveFollowedArtists(user)
            call.respond(response)
        }
    }
}