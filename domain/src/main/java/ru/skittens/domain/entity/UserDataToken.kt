package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserDataToken(
    val token: String
)