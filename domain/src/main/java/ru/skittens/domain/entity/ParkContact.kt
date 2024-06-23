package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ParkContact(
    val email: String,
    val id: Int,
    val phone: String
)