package ru

import io.ktor.openapi.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureSwagger() {
    routing {
        openAPI(path = "openapi") {
            info = OpenApiInfo("My API", "1.0")
        }

        swaggerUI(
            path = "swagger"
        )
    }
}
