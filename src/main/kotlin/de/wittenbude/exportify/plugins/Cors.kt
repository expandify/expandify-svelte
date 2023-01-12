package de.wittenbude.exportify.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*

fun Application.configureCors() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.Authorization)
        anyHost()
    }
}