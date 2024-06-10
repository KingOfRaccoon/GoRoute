package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationUserData(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String
)