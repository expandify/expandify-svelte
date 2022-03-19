package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.AlbumService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.albumRouting() {
    route("/album") {
        get {
            val user = requestUser
            val response = AlbumService.getUserAlbums(user)
            call.respond(response)
        }
        post {
            val user = requestUser
            val response = AlbumService.saveUserAlbums(user)
            call.respond(response)
        }
    }
}