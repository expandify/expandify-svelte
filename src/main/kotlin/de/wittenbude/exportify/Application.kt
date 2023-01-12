package de.wittenbude.exportify

import de.wittenbude.exportify.connectors.SpotifyApiConnector.Companion.configureSpotifyApi
import de.wittenbude.exportify.connectors.configureKMongo
import de.wittenbude.exportify.plugins.configureCors
import de.wittenbude.exportify.plugins.configureRouting
import de.wittenbude.exportify.plugins.configureSecurity
import de.wittenbude.exportify.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSpotifyApi()
    configureSecurity()
    configureRouting()
    configureSerialization()
    configureKMongo()
    configureCors()
}
