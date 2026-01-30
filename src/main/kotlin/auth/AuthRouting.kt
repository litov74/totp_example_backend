package ru.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import ru.dto.LoginRequest
import ru.dto.LoginResponse

fun Application.authRoutes() {
    routing {
        post("/login") {
            val body = call.receive<LoginRequest>()
            if (body.login != "ivanov") {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            //TODO: убрать заглушку
            if (body.totp != "123456") {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            call.respond(LoginResponse(token = "demo-token"))
        }
    }
}