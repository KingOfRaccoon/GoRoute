package ru.skittens.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class IncidentRequest(
    val comment: String,
    val incidentTypeId: Int,
    val latitude: Double,
    val longitude: Double,
    val threadDegreeId: Int,
    val sourceId: Int = 1
)