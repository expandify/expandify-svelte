package de.wittenbude.exportify.routes

import de.wittenbude.exportify.models.requestUser
import de.wittenbude.exportify.services.getUserAlbums
import de.wittenbude.exportify.services.saveUserAlbums
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.albumRouting() {
    route("/album") {
        get {
            val response = getUserAlbums(requestUser)
            call.respond(response)
        }
        post {
            val response = saveUserAlbums(requestUser)
            call.respond(response)
        }
    }
}
