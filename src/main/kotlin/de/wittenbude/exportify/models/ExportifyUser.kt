@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package de.wittenbude.exportify.models

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExportifyUser(
    @SerialName("_id") val spotifyId: String?,
    val accessToken: String,
    val refreshToken: String
) : Principal

val PipelineContext<Unit, ApplicationCall>.requestUser get() = call.authentication.principal<ExportifyUser>()!!
