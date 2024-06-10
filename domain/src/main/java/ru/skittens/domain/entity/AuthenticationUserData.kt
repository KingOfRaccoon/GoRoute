package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationUserData(
    val email: String,
    val password: String
)
