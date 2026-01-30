package ru

import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testLoginFailWrongTotp() = testApplication {
        application {
            module()
        }

        val response = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                  "login": "ivanov",
                  "totp": "000000"
                }
                """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun testLoginSuccess() = testApplication {
        application {
            module()
        }

        val response = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                {
                  "login": "ivanov",
                  "totp": "123456"
                }
                """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("demo-token"))
    }

    @Test
    fun testGradesAuthorized() = testApplication {
        application {
            module()
        }

        val response = client.get("/grades") {
            header(HttpHeaders.Authorization, "Bearer demo-token")
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("Математика"))
    }

    @Test
    fun testGradesUnauthorized() = testApplication {
        application {
            module()
        }

        val response = client.get("/grades")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

}
