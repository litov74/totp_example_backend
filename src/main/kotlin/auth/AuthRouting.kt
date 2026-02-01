package ru.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import ru.TotpUtil
import ru.dto.LoginRequest
import ru.dto.LoginResponse
import ru.totp_sample_secret_key

fun Application.authRoutes() {
    routing {
        post("/login") {
            val body = call.receive<LoginRequest>()
            if (body.login != "ivanov") {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            val now = System.currentTimeMillis()

            val valid = listOf(
                TotpUtil.generateTotp(totp_sample_secret_key, now - 30_000),
                TotpUtil.generateTotp(totp_sample_secret_key, now),
                TotpUtil.generateTotp(totp_sample_secret_key, now + 30_000)
            ).contains(body.totp)

            if (!valid) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }
            call.response.cookies.append(
                Cookie(
                    name = "token",
                    value = "demo-token",
                    httpOnly = true,
                    path = "/"
                )
            )
            call.respond(LoginResponse(token = "demo-token"))
        }
        post("/logout") {
            call.response.cookies.append(
                Cookie(
                    name = "token",
                    value = "",
                    path = "/",
                    maxAge = 0
                )
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}