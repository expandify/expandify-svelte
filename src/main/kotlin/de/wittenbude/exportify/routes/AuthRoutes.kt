package de.wittenbude.exportify.routes

import de.wittenbude.exportify.services.authenticate
import de.wittenbude.exportify.services.buildAuthRedirectUri
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    route("") {
        get("/authorize") {
            var state = call.request.queryParameters["redirect_uri"]
            if (state == null) {
                state = List(16) { (('a'..'z') + ('A'..'Z') + ('0'..'9')).random() }.joinToString("")
            }
            val authUri = buildAuthRedirectUri(state)
            call.respondRedirect(authUri)
        }
        get("/callback") {
            val token = authenticate(call)
            println("------------------------")
            println(call.request.queryParameters["code"])

            val state = call.request.queryParameters["state"]
            println(state)
            println(Url.isValid(state))
            if (!Url.isValid(state) || token == null) {
                call.respond(token ?: HttpStatusCode.Unauthorized)
            }
            val url = URLBuilder()
            url.takeFrom(state!!).parameters.append("code", token!!)
            call.respondRedirect(url.buildString())
        }
    }
}

fun Url.Companion.isValid(str: String?): Boolean {
    if (str == null) {
        return false
    }
    try {
        Url(str)
    } catch (e: Throwable) {
        return false
    }
    return true
}