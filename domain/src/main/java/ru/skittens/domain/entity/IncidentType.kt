package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class IncidentType(
    val id: Int,
    val name: String
)