package ru.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val login: String,
    val totp: String
)

@Serializable
data class LoginResponse(
    val token: String
)

@Serializable
data class Grade(
    val subject: String,
    val value: Int
)