package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val firstname: String,
    val id: Int,
    val lastname: String,
    val role: String,
    val token: String
)