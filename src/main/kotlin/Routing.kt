package ru

import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureBasicRoutes() {
    routing {
        staticResources("/", "static")
    }
}
