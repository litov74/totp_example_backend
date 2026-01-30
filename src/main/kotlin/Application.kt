package ru

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.serialization.json.Json
import ru.auth.authRoutes
import ru.grades.gradesRoutes

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {

    install(CORS) {
        allowHost("0.0.0.0:8081")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
    configureBasicRoutes()
    authRoutes()
    gradesRoutes()
    configureSwagger()

}
