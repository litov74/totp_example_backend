package ru.grades

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.dto.Grade

fun Application.gradesRoutes() {
    routing {
        get("/grades") {
            val auth = call.request.headers["Authorization"]
            if (auth != "Bearer demo-token") {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }
            call.respond(
                listOf(
                    Grade("Математика", 5),
                    Grade("Физика", 4),
                    Grade("Информатика", 5)
                )
            )
        }
    }
}