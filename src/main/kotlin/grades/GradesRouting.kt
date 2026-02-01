package ru.grades

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import ru.dto.Grade

fun Application.gradesRoutes() {
    routing {
        get("/grades") {
            val token = call.request.cookies["token"]
            if (token != "demo-token") {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }
            val marks = listOf(
                Grade("Математика", 5),
                Grade("Физика", 4),
                Grade("Информатика", 5)
            )
            val model = mapOf(
                "grades" to marks
            )
            call.respond(ThymeleafContent("grades", model))
        }
    }
}