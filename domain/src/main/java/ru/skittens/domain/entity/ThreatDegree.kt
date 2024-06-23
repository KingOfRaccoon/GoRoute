package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ThreatDegree(
    val id: Int,
    val name: String
)